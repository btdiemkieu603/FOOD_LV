package com.kt.backend.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.CategoryDto;
import com.kt.backend.dto.ProductDto;
import com.kt.backend.entity.Category;
import com.kt.backend.entity.Product;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.CategoryRepository;
import com.kt.backend.repository.ProductRepository;
import com.kt.backend.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	//Thêm 1 sản phẩm
	@Override
	public ProductDto createProduct(ProductDto productDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "categoryId", categoryId));
		Product product = this.modelMapper.map(productDto, Product.class);
		product.setCategory(category);
		
		Product newProduct = this.productRepo.save(product);
		return this.modelMapper.map(newProduct, ProductDto.class);
	}

	//Sửa thông tin 1 sản phẩm
	@Override
	public ProductDto updateProduct(ProductDto productDto, Integer productId) {
		
		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product ", "ProductId", productId));
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setUrl_image_product(productDto.getUrl_image_product());
		
		Product updateProduct = this.productRepo.save(product);
				
		return this.modelMapper.map(updateProduct, ProductDto.class);

	}

	//Xóa 1 sản phẩm
	@Override
	public void deleteProduct(Integer productId) {
		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
		this.productRepo.delete(product);
	}

	//Láy 1 sản phẩm
	@Override
	public ProductDto getProductById(Integer productId) {
		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
		return this.modelMapper.map(product, ProductDto.class);
	}

	//Lấy ds sản phẩm theo danh mục
	@Override
	public List<ProductDto> getProductsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category", categoryId));
		List<Product> products = this.productRepo.findByCategory(category);
		List<ProductDto> productDtos = products.stream().map((product) -> this.modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());

		return productDtos;
	}
	
	@Override
	public List<ProductDto> getAllProducts() {		
		List<Product> products = this.productRepo.findAll();
		List<ProductDto> productDtos = products.stream().map((product) -> this.modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
		return productDtos;
	}

	@Override
    public ProductDto changeExist(Integer id) {
        Product product = this.productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        product.setIsExist(!product.getIsExist());
        Product updatedProduct = this.productRepo.save(product);

        return this.modelMapper.map(updatedProduct, ProductDto.class);
    }
	
//	public List<ProductDto> getSuggestedProducts(Integer userId) {
//	    // Dựa vào hành vi người dùng để gợi ý sản phẩm, ví dụ: lịch sử mua hàng, đánh giá, v.v.
//	    return productRepository.findSuggestionsByUserId(userId);
//	}
	
}
