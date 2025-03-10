# from scripts.recommendation import recommend_products
#
# def main():
#     API_URL = "http://localhost:8080/api/order/all"
#     USER_ID = 9  # Thay bằng ID người dùng cần gợi ý
#     TOP_N = 5    # Số lượng sản phẩm gợi ý
#
#     recommendations = recommend_products(API_URL, USER_ID, TOP_N)
#     if recommendations:
#         print(f"Gợi ý sản phẩm cho người dùng {USER_ID}: {recommendations}")
#     else:
#         print(f"Không có gợi ý nào cho người dùng {USER_ID}.")
#
# if __name__ == "__main__":
#     main()

from flask import Flask, request, jsonify
from scripts.recommendation import recommend_products
import requests

app = Flask(__name__)


# Hàm lấy chi tiết sản phẩm
def fetch_product_details(product_id):
    api_url = f"http://localhost:8080/api/product/{product_id}"
    try:
        response = requests.get(api_url)
        response.raise_for_status()
        # return response.json()
        product_data = response.json()
        if product_data:
            return {
                "id": product_data.get("id"),
                "name": product_data.get("name"),
                "price": product_data.get("price"),
                "description": product_data.get("description"),
                "isExist": product_data.get("isExist"),
                "url_image_product": product_data.get("url_image_product"),
                "category": {
                    "id": product_data["category"].get("id"),
                    "title": product_data["category"].get("title"),
                    "url_image_category": product_data["category"].get("url_image_category"),
                    "isExist": product_data["category"].get("isExist")
                }
            }
    except requests.exceptions.RequestException as e:
        print(f"Error fetching product {product_id}: {e}")
        return None

API_URL = "http://localhost:8080/api/order/all"


@app.route('/api/recommendation/<int:user_id>', methods=['GET'])
def recommend(user_id):
    top_n = request.args.get('top_n', 5)  # Số lượng sản phẩm gợi ý, mặc định là 5

    recommendations = recommend_products(API_URL, user_id, int(top_n))  # Gọi hàm gợi ý

    if not recommendations:
        return jsonify([])

    if recommendations:
        # Lấy thông tin chi tiết cho từng sản phẩm
        detailed_products = []
        for product_id in recommendations:
            product_details = fetch_product_details(product_id)
            if product_details:
                detailed_products.append(product_details)

        return jsonify(detailed_products)

        #
        # return jsonify({
        #     # 'user_id': user_id,
        #     'recommended_products': detailed_products
        #     # '': detailed_products
        #
        # })
    else:
        return jsonify({
            'message': 'Không có gợi ý nào cho người dùng {}'.format(user_id)
        }), 404


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5001)  # Cấu hình cổng 5000