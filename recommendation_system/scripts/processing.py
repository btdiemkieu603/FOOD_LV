import pandas as pd

def preprocess_orders(orders_data):
    """
    Đầu vào: Danh sách các đơn hàng
    Đầu ra: pd.DataFrame: DataFrame chứa thông tin account_id, product_id và quantity.
    """
    rows = []
    for order in orders_data:
        if order['orderStatus']['status'] == 'Completed':
            account_id = order['account']['id']
            for item in order['items']:
                product_id = item['product']['id']
                quantity = item['quantity']
                rows.append({
                    'account_id': account_id,
                    'product_id': product_id,
                    'quantity': quantity
                })
    print("\nDataFrame sau khi xử lý đơn hàng:")
    print(pd.DataFrame(rows))
    return pd.DataFrame(rows)

def create_user_product_matrix(orders_df):
    """
    Tạo ma trận người dùng x sản phẩm từ DataFrame đơn hàng.
    Đầu vào:  orders_df (pd.DataFrame): DataFrame chứa account_id, product_id, quantity.\
    Đầu ra: pd.DataFrame: Ma trận người dùng x sản phẩm (Pivot Table).
    """
    pivot_df = orders_df.pivot_table(
        index='account_id',
        columns='product_id',
        values='quantity',
        aggfunc='sum',
        fill_value=0
    )
    print("\nMa trận người dùng x sản phẩm (Pivot Table):")
    print(pivot_df)
    return pivot_df