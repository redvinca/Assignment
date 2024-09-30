import requests
import json

base_url = "http://localhost:8080/products"

def get_item_by_id(item_id):
    url = f"{base_url+"/getProductById"}/{item_id}"
    response = requests.get(url)
    if response.status_code == 200:
        json_str=json.dumps(response.json(),indent=4)
        print(json_str)
    else:
        print(f"Failed to fetch item with ID {item_id}, Status Code: {response.status_code}")
        print(response.text)

def get_all_items():
    response = requests.get(base_url+"/getAllProducts")
    if response.status_code == 200:
        #print("Items:", response.json())
        json_str=json.dumps(response.json(),indent=4)
        print(json_str)
    else:
        print(f"Failed to fetch items, Status Code: {response.status_code}")



# ----------------create product --------------------------------

def create_product(product_name, product_description, price, image_path):
    create_product_url = f"{base_url}/createProduct"
    
    product_data = {
        "name": product_name,
        "description": product_description,
        "price": price
    }
    
    image_file = open(image_path, "rb")
    
    files = {
        "file": ("watch.jpg", image_file, "image/jpeg"),
        "product": (None, json.dumps(product_data), "application/json")
    }
    
    try:
        response = requests.post(create_product_url, files=files)
        response.raise_for_status()  
        print("Product created successfully!")
    except requests.exceptions.RequestException as e:
        print(f"Error creating product: {e}")
    finally:
        image_file.close()

#----------------------------------------------cart ---------------------------------------------------

BASE_URL = "http://localhost:8080" 
Cart_API_URL = f"{BASE_URL}/cart"
headers = {'Content-Type': 'application/json'}

# Function to create a new cart 
def addtoCart(product_id):
    url = f"{Cart_API_URL}/addToCart"
   
    # Create a dictionary with the product ID
    product_data = {"productId": product_id}
   
    # Convert the dictionary to JSON
    product_json = json.dumps(product_data)
   
    
   
    response = requests.post(url, data=product_json, headers=headers)
   
    # Check the status of the response
    if response.status_code == 200:
        print("cart Added successfully.")
    else:
        print(f"Failed to Add cart. Status code: {response.status_code}")
        print(response.text)



#      2. getAllcartitems

def get_all_items1():
    response = requests.get(Cart_API_URL+"/getAllCartItems")
    if response.status_code == 200:
        #print("Items:", response.json())
        json_str=json.dumps(response.json(),indent=4)
        print(json_str)
    else:
        print(f"Failed to fetch items, Status Code: {response.status_code}")



#     3. getTotalPrice

def get_total_price():
    response=requests.get(Cart_API_URL+"/getTotalPrice")
    json_str=json.dumps(response.json())
    print("Total price :",json_str)


#     4. getTotalQuantity

def get_total_quantity():
    response=requests.get(Cart_API_URL+"/getTotalQuantity")
    json_str=json.dumps(response.json())
    print("Total quantity :",json_str)


#    5. updateQuantity

def updateQuantity(cart_id):
    url = f"{Cart_API_URL}/updateQuantity"
    cart_data={
        "cartItemId":cart_id,
        "quantityChange":2
    }

    product_json = json.dumps(cart_data)
    response = requests.post(url, data=product_json, headers=headers)
   
    # Check the status of the response
    if response.status_code == 200:
        print("quantity updated successfully.")
    else:
        print(f"Failed to update. Status code: {response.status_code}")
        print(response.text)
    


#---------------------clear selected items 

def clear_selected_items():
    url=f"{Cart_API_URL}/clearSelectedItems"
    id_list = [7]
    response=requests.post(url,json=id_list,headers=headers)
    if response.status_code==200:
        print("cleared selected items successfully")
    else:
        print(f"failed to clear , status code : {response.status_code}")
        print(response.text)


def delete_cart_item():
    url=f"{Cart_API_URL}/deleteItemFromCart"
    cart_data={
        "cartId":9
    }
    cart_json=json.dumps(cart_data)
    response=requests.post(url,data=cart_json,headers=headers)
    if response.status_code==200:
        print("cart item deleted successfully")
    else:
        print(f"unable to delete status_code :{response.status_code}")
        print(response.text)

# ---------------------------------------calling function --------------------------------------------------



addtoCart(1)
get_all_items1()
get_total_price()
get_total_quantity()
updateQuantity(7)
clear_selected_items()
delete_cart_item()


# products ---------
get_all_items()
get_item_by_id(2)
create_product("watch", "This is a test product", 3, r"C:\Users\shwet\Desktop\watch.jpg")