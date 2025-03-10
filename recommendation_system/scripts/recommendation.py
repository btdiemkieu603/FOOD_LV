
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
from scripts.api import fetch_orders
from scripts.processing import preprocess_orders, create_user_product_matrix


def calculate_similarity(pivot_df):
    """
    Tính độ tương đồng giữa các người dùng dựa trên ma trận người dùng x sản phẩm.
    Đầu vào: pivot_df (pd.DataFrame): Ma trận người dùng x sản phẩm.
    Đầu ra: pd.DataFrame: Ma trận độ tương đồng giữa các người dùng.
    """
    cosine_sim = cosine_similarity(pivot_df)
    cosine_sim_df = pd.DataFrame(cosine_sim, index=pivot_df.index, columns=pivot_df.index)
    print("\nMa trận tương đồng giữa các người dùng:")
    print(cosine_sim_df)
    return cosine_sim_df


def get_popular_products(pivot_df, top_n):
    """
    Lấy danh sách các sản phẩm phổ biến nhất dựa trên tổng số lượng mua.
    Đầu vào:
        pivot_df (pd.DataFrame): Ma trận người dùng x sản phẩm.
        top_n (int): Số lượng sản phẩm cần lấy.
    Đầu ra: list: Danh sách các product_id phổ biến.
    """
    popular_products = pivot_df.sum(axis=0).sort_values(ascending=False).index.tolist()
    print(f"Sản phẩm phổ biến: {popular_products}")
    return popular_products[:top_n]


def get_top_user_products(user_products, top_n):
    """
    Lấy danh sách các sản phẩm mà người dùng đã mua nhiều nhất.
    Đầu vào:
        user_products (pd.Series): Danh sách sản phẩm của người dùng với số lượng đã mua.
        top_n (int): Số lượng sản phẩm cần lấy.
    Đầu ra: list: Danh sách các product_id mà người dùng đã mua nhiều nhất.
    """
    top_products = user_products.sort_values(ascending=False).head(top_n).index.tolist()
    print(f"Sản phẩm người dùng đã mua nhiều nhất: {top_products}")
    return top_products


def get_recommendations(user_id, cosine_sim_df, pivot_df, top_n=5):
    """
    Lấy danh sách sản phẩm gợi ý cho một người dùng cụ thể, với việc xét người dùng tương đồng từ cao đến thấp.
    Cách triển khai
        1. lấy món ăn gợi ý theo thứ tự danh sách nguời dùng tương đồng từ cao đến thấp
        2. Khi lọc ấy hết danh sách người dùng, nếu không đủ 5 món thì tiếp tục lấy danh sách món người dùng mua nhiều nhất để gợi ý
        3. nếu vẫn chưa đủ, thêm gợi ý các món ăn phổ biến
    Args:
        user_id (int): ID người dùng cần gợi ý.
        cosine_sim_df (pd.DataFrame): Ma trận độ tương đồng giữa người dùng.
        pivot_df (pd.DataFrame): Ma trận người dùng x sản phẩm.
        top_n (int): Số lượng sản phẩm gợi ý.
    Returns:
        list: Danh sách các product_id được gợi ý.

    Tại sao lọc theo danh sách người dùng tương đồng nhưng vẫn không đủ 5 món
        1. người dùng mua hầu hết các món hiện có của cửa hàng
        2. dữ liệu người dùng tương đồng với người dùng cần gợi ý rất ít
    Đối với người dùng mới (chưa mua hàng lần nào)
        1. Trả về rỗng
        2. Gợi ý món ăn phổ biến
    Có thể phát triển thêm lọc theo danh sách món ăn được thêm va giỏ hàng của người dùng => tăng độ phong phú dữ liệu và chính xác
    """
    if user_id not in cosine_sim_df.index or user_id not in pivot_df.index:
        print(f"Người dùng {user_id} không có trong dữ liệu. Trả về danh sách rỗng.")
        return []

    # Lấy độ tương đồng của người dùng mục tiêu
    sim_scores = cosine_sim_df[user_id]
    similar_users = sim_scores.sort_values(ascending=False).index.tolist()
    similar_users.remove(user_id)  # Loại bỏ chính người dùng mục tiêu

    print(f"Người dùng tương đồng với {user_id}: {similar_users}")

    # Lấy các sản phẩm mà người dùng mục tiêu chưa mua
    user_products = pivot_df.loc[user_id]
    user_products_unpurchased = user_products[user_products == 0].index.tolist()

    print(f"Sản phẩm chưa mua bởi người dùng {user_id}: {user_products_unpurchased}")

    # Tìm sản phẩm gợi ý từ người dùng tương đồng
    recommendations = []
    for similar_user in similar_users:
        similar_user_products = pivot_df.loc[similar_user]

        # Thêm các sản phẩm mà người dùng tương tự đã mua, nhưng người dùng mục tiêu chưa mua
        for product_id in similar_user_products.index:
            if product_id in user_products_unpurchased and similar_user_products[product_id] > 0:
                recommendations.append(product_id)

        # Loại bỏ trùng lặp trong danh sách gợi ý
        recommendations = list(set(recommendations))

        print(f"Recommendations after considering user {similar_user}: {recommendations}")

    # Bổ sung sản phẩm từ danh sách đã mua nhiều nhất của chính người dùng nếu vẫn chưa đủ
    if len(recommendations) < top_n:
        print("Chưa đủ sản phẩm, bổ sung sản phẩm đã mua nhiều nhất của chính người dùng...")
        top_user_products = get_top_user_products(user_products, top_n)
        print(f"Top user products to add: {top_user_products}")  # Hiển thị danh sách sản phẩm dự định bổ sung
        for product_id in top_user_products:
            if product_id not in recommendations:
                recommendations.append(product_id)
                print(f"Added {product_id} from user top products. Current recommendations: {recommendations}")
            if len(recommendations) >= top_n:
                break

    # Nếu vẫn không đủ, bổ sung sản phẩm phổ biến
    if len(recommendations) < top_n:
        print("Chưa đủ sản phẩm, bổ sung sản phẩm phổ biến...")
        popular_products = get_popular_products(pivot_df, top_n)
        print(f"Popular products to add: {popular_products}")  # Hiển thị danh sách sản phẩm phổ biến dự định bổ sung
        for product_id in popular_products:
            if product_id not in recommendations and product_id in user_products_unpurchased:
                recommendations.append(product_id)
                print(f"Added {product_id} from popular products. Current recommendations: {recommendations}")
            if len(recommendations) >= top_n:
                break

    print(f"Final recommendations for user {user_id}: {recommendations}")
    return recommendations[:top_n]  # Trả về đúng số lượng sản phẩm yêu cầu


def recommend_products(api_url, user_id, top_n=5):
    """
    Gợi ý sản phẩm cho người dùng dựa trên dữ liệu thực tế từ API.
    Args:
        api_url (str): URL API.
        user_id (int): ID người dùng cần gợi ý.
        top_n (int): Số lượng sản phẩm gợi ý.
    Returns:
        list: Danh sách product_id gợi ý.
    """
    # Lấy dữ liệu đơn hàng từ API
    orders_data = fetch_orders(api_url)
    if not orders_data:
        print("Không lấy được dữ liệu từ API.")
        return []

    # Xử lý dữ liệu đơn hàng
    orders_df = preprocess_orders(orders_data)
    if orders_df.empty:
        print("Dữ liệu sau khi xử lý rỗng.")
        return []


    # Tạo ma trận người dùng x sản phẩm
    pivot_df = create_user_product_matrix(orders_df)
    # if pivot_df.empty or user_id not in pivot_df.index:
    #     print(f"Người dùng {user_id} không có trong dữ liệu hoặc không có lịch sử mua hàng.")
    #     return []

    if user_id not in pivot_df.index:
        print(f"Người dùng {user_id} chưa có lịch sử mua hàng.")
        return []  # Trả về danh sách rỗng để hàm gọi bổ sung gợi ý


    # Tính độ tương đồng giữa các người dùng
    cosine_sim_df = calculate_similarity(pivot_df)

    # Lấy gợi ý sản phẩm
    return get_recommendations(user_id, cosine_sim_df, pivot_df, top_n)