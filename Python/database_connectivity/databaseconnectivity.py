import mysql.connector


#creating connection object 

myconn=mysql.connector.connect(
    host="localhost",
    user="root",
    password="root"
)


cursor=myconn.cursor()
cursor.execute("use shoppingcartdb")
    

#create product , get all products , get product by id 

#---------------------------------------CREATE PRODUCT ----------------------------------------------------

def createProduct(name, description, price, quantity):
    try:
        query = "INSERT INTO Product (name, description, price, stock) VALUES (%s, %s, %s, %s)"
        
        # Insert the actual values (these are placeholders to prevent SQL injection)
        values = (name, description, price, quantity)
        
        cursor.execute(query, values)
        myconn.commit()

        print("Product inserted successfully!")
    
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        myconn.rollback()


#--------------------------------------GET ALL PRODUCTS----------------------------------------------------

def getAllProducts():
    try:
        query="select * from product"
        cursor.execute(query)
        result =cursor.fetchall()
        for x in result:
            print(x)
    except mysql.connector.Error as err:
        print(f"error :{err}")
        myconn.rollback()

#--------------------------------------GET PRODUCT BY ID--------------------------------------------------

def getProductById(id):
    try:
        query="select * from product where id=%s"
        value=(id,)
        cursor.execute(query,value)
        result=cursor.fetchone()
        print()
        print()
        print(result)
    except mysql.connector.Error as err:
        print(f"error:{err}")
        myconn.rollback()


#--------------------------------------------        cart       --------------------------------------------





# add to cart , get all cart items , get total price , get total quantity, update quantity ,delete item from cart 


def addToCart(productId,quantity):
    try:
        check="select * from cart where product_id=%s"
        value=(productId,)
        cursor.execute(check,value)
        result=cursor.fetchone()
        if result is None:
            query="insert into cart(quantity,product_id) select %s,%s from product where id=%s and stock>%s"
            value=(quantity,productId,productId,quantity)
            cursor.execute(query,value)
            if cursor.rowcount > 0:
                myconn.commit()
                print("Product inserted successfully!")
            else:
                print("Insufficient stock.")
        else:
            print("Product already exists in the cart !")

    except mysql.connector.Error as err:
        print(f"error:{err}") 
        myconn.rollback()
    
#-----------------------GET ALL CART ITEMS------------------------------

def getAllCartItems():
    try:
        query="select * from cart"
        cursor.execute(query)
        result=cursor.fetchall()
        for x in result:
            print(x)
    except mysql.connector.Error as err:
        print(f"Error :{err}")
        myconn.rollback()


#-------------------GET TOTAL PRICE-----------------------------------

def getTotalPrice():
    try:
        query="select sum(quantity*(select price from product where id=cart.product_id) ) as total_price from cart"
        cursor.execute(query)
        result=cursor.fetchone()

        if result[0] is not None:
            print(f"Total price :{result[0]}")  # Display the value directly
        else:
         print("No products in the cart ")
        
        
    except mysql.connector.Error as err:
        print(f"Error :{err}")
        myconn.rollback()

#-----------------------------------GET TOTAL QUANTITY--------------------------------

def getTotalQuantity():
    try:
        query="select sum(quantity) from cart"
        cursor.execute(query)
        result=cursor.fetchone()
        if result[0] is not None:
            print(f"Total quanity: {result[0]}")
        else:
            print("No products in the cart")
    except mysql.connector.Error as err:
        print(f"Error :{err}")
        myconn.rollback()


#--------------------------UPDATE QUANTITY--------------------------------------

def updateQuantity(productId,quantity):
    try:
        check="select * from cart where product_id=%s"
        value=(productId,)
        cursor.execute(check,value)
        result=cursor.fetchone()
        if result is not None:
            query="update cart set quantity=%s where product_id=%s"
            value=(quantity,productId)
            cursor.execute(query,value)
            myconn.commit()
            print("quantity updated")
        else :
            print("product not is cart")
    except mysql.connector.Error as err:
        print(f"Error :{err}")
        myconn.rollback()

#----------------------------------DELETE ITEM FROM CART ------------------------------------


def deleteItemFromCart(cartId):
    try:
        check="select * from cart where id=%s"
        value=(cartId,)
        cursor.execute(check,value)
        result=cursor.fetchone()
        if result is not None:
            query="delete from cart where id=%s"
            value=(cartId,)
            cursor.execute(query,value)
            myconn.commit()
            print("product from cart is deleted successfully..")
        else:
            print("Product not in cart")
    except mysql.connector.Error as err:
        print(f"Error :{err}")
        myconn.rollback()
            
    


createProduct("abc", "abc", 300, 3)
getAllProducts()
getProductById(2)
addToCart(2,1)
getAllCartItems()
getTotalPrice()
getTotalQuantity()
updateQuantity(10,2)
deleteItemFromCart(10)

myconn.close()

