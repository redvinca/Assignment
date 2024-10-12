from flask import Flask, request, jsonify
import database_connectivity.databaseconnectivity as db  # Import your database connectivity module

app = Flask(__name__)

# --------------------------------------- CREATE PRODUCT ----------------------------------------------------
@app.route('/product', methods=['POST'])
def create_product():
    try:
        data = request.json
        name = data.get('name')
        description = data.get('description')
        price = data.get('price')
        quantity = data.get('quantity')

        # Call the database method to create the product
        db.createProduct(name, description, price, quantity)
        return jsonify({"message": "Product inserted successfully!"}), 201

    except Exception as err:
        return jsonify({"error": str(err)}), 500

# -------------------------------------- GET ALL PRODUCTS ----------------------------------------------------
@app.route('/products', methods=['GET'])
def get_all_products():
    try:
        products = db.getAllProducts()
        return jsonify(products), 200
    except Exception as err:
        return jsonify({"error": str(err)}), 500

# -------------------------------------- GET PRODUCT BY ID --------------------------------------------------
@app.route('/product/<int:id>', methods=['GET'])
def get_product_by_id(id):
    try:
        product = db.getProductById(id)
        if product:
            return jsonify(product), 200
        return jsonify({"message": "Product not found"}), 404
    except Exception as err:
        return jsonify({"error": str(err)}), 500

# -------------------------------------------- ADD TO CART ---------------------------------------------------
@app.route('/cart', methods=['POST'])
def add_to_cart():
    try:
        data = request.json
        product_id = data.get('product_id')
        quantity = data.get('quantity')
        db.addToCart(product_id, quantity)
        return jsonify({"message": "Added to cart"}), 201
    except Exception as err:
        return jsonify({"error": str(err)}), 500

# ----------------------- GET ALL CART ITEMS ---------------------------------------------------
@app.route('/cart', methods=['GET'])
def get_all_cart_items():
    try:
        cart_items = db.getAllCartItems()
        return jsonify(cart_items), 200
    except Exception as err:
        return jsonify({"error": str(err)}), 500

# ------------------- GET TOTAL PRICE ---------------------------------------------------------
@app.route('/cart/total-price', methods=['GET'])
def get_total_price():
    try:
        total_price = db.getTotalPrice()
        return jsonify({"total_price": total_price}), 200
    except Exception as err:
        return jsonify({"error": str(err)}), 500

# ------------------- GET TOTAL QUANTITY ---------------------------------------------------------
@app.route('/cart/total-quantity', methods=['GET'])
def get_total_quantity():
    try:
        total_quantity = db.getTotalQuantity()
        return jsonify({"total_quantity": total_quantity}), 200
    except Exception as err:
        return jsonify({"error": str(err)}), 500

# -------------------------- UPDATE QUANTITY ------------------------------------------------------
@app.route('/cart/<int:product_id>', methods=['PUT'])
def update_quantity(product_id):
    try:
        data = request.json
        quantity = data.get('quantity')
        db.updateQuantity(product_id, quantity)
        return jsonify({"message": "Quantity updated"}), 200
    except Exception as err:
        return jsonify({"error": str(err)}), 500

# ---------------------------------- DELETE ITEM FROM CART -------------------------------------------------
@app.route('/cart/<int:cart_id>', methods=['DELETE'])
def delete_item_from_cart(cart_id):
    try:
        db.deleteItemFromCart(cart_id)
        return jsonify({"message": "Product deleted from cart"}), 200
    except Exception as err:
        return jsonify({"error": str(err)}), 500

if __name__ == '__main__':
    app.run(debug=True)
