import requests

def fetch_orders(api_url):
    """Hàm này sẽ gọi API và lấy tất cả dữ liệu đơn hàng."""
    try:
        response = requests.get(api_url)
        response.raise_for_status()  # Kiểm tra lỗi HTTP
        return response.json()  # Trả về dữ liệu dưới dạng JSON
    except requests.exceptions.RequestException as e:
        print(f"Error fetching data from API: {e}")
        return []
