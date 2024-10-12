import mysql.connector

# Creating connection object
myconn = mysql.connector.connect(
    host="localhost",
    user="root",
    password="root",
    database="shoppingcartdb"
)
cursor = myconn.cursor()

# --------------------------------------- CREATE PRODUCT ----------------------------------------------------
def createProduct(name, description, price, quantity):
    try:
        query = "INSERT INTO Product (name, description, price, stock) VALUES (%s, %s, %s, %s)"
        values = (name, description, price, quantity)
        cursor.execute(query, values)
        myconn.commit()
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        myconn.rollback()

# -------------------------------------- GET ALL PRODUCTS ----------------------------------------------------
def getAllProducts():
    try:
        query = "SELECT * FROM product"
        cursor.execute(query)
        result = cursor.fetchall()
        return result
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        myconn.rollback()

# -------------------------------------- GET PRODUCT BY ID --------------------------------------------------
def getProductById(id):
    try:
        query = "SELECT * FROM product WHERE id = %s"
        cursor.execute(query, (id,))
        result = cursor.fetchone()
        return result
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        myconn.rollback()

# -------------------------------------------- ADD TO CART ---------------------------------------------------
def addToCart(productId, quantity):
    try:
        check = "SELECT * FROM cart WHERE product_id = %s"
        cursor.execute(check, (productId,))
        result = cursor.fetchone()
        if result is None:
            query = "INSERT INTO cart (quantity, product_id) SELECT %s, %s FROM product WHERE id = %s AND stock > %s"
            cursor.execute(query, (quantity, productId, productId, quantity))
            if cursor.rowcount > 0:
                myconn.commit()
            else:
                print("Insufficient stock.")
        else:
            print("Product already exists in the cart.")
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        myconn.rollback()

# ----------------------- GET ALL CART ITEMS ---------------------------------------------------
def getAllCartItems():
    try:
        query = "SELECT * FROM cart"
        cursor.execute(query)
        result = cursor.fetchall()
        return result
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        myconn.rollback()

# ------------------- GET TOTAL PRICE ---------------------------------------------------------
def getTotalPrice():
    try:
        query = "SELECT SUM(quantity * (SELECT price FROM product WHERE id = cart.product_id)) AS total_price FROM cart"
        cursor.execute(query)
        result = cursor.fetchone()
        return result[0] if result else 0
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        myconn.rollback()

# ------------------- GET TOTAL QUANTITY ---------------------------------------------------------
def getTotalQuantity():
    try:
        query = "SELECT SUM(quantity) FROM cart"
        cursor.execute(query)
        result = cursor.fetchone()
        return result[0] if result else 0
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        myconn.rollback()

# -------------------------- UPDATE QUANTITY ------------------------------------------------------
def updateQuantity(productId, quantity):
    try:
        # Check if the product is in the cart
        check = "SELECT * FROM cart WHERE product_id = %s"
        cursor.execute(check, (productId,))
        result = cursor.fetchone()
        
        if result is not None:
            # Check if there's enough stock
            stock_check = "SELECT stock FROM product WHERE id = %s"
            cursor.execute(stock_check, (productId,))
            stock = cursor.fetchone()[0]
            
            if stock >= quantity:
                # Update the quantity
                query = "UPDATE cart SET quantity = %s WHERE product_id = %s"
                cursor.execute(query, (quantity, productId))
                myconn.commit()
                return True, "Quantity updated successfully"
            else:
                return False, "Insufficient stock"
        else:
            return False, "Product not in cart"
    
    except mysql.connector.Error as err:
        myconn.rollback()
        return False, f"Database error: {err}"

# ---------------------------------- DELETE ITEM FROM CART -------------------------------------------------
def deleteItemFromCart(cartId):
    try:
        check = "SELECT * FROM cart WHERE id = %s"
        cursor.execute(check, (cartId,))
        result = cursor.fetchone()
        if result is not None:
            query = "DELETE FROM cart WHERE id = %s"
            cursor.execute(query, (cartId,))
            myconn.commit()
        else:
            print("Product not in cart.")
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        myconn.rollback()
