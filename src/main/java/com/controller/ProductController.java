package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.VO.ProductVO;
import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.Spanner;
import com.google.cloud.spanner.SpannerOptions;
import com.google.cloud.spanner.Statement;
import com.google.cloud.spanner.TransactionContext;
import com.google.cloud.spanner.TransactionRunner.TransactionCallable;
import com.modals.Product;
import com.repository.ProductRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/ecart")
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@PostMapping("/create")
	public ResponseEntity<Product> create(@Valid @RequestBody ProductVO productVO) {
		Product product = new Product();
		BeanUtils.copyProperties(productVO, product);
		product = productRepository.save(product);
		return new ResponseEntity<Product>(HttpStatus.OK);

	}

	@GetMapping("/get")
	public ResponseEntity<List<Product>> get() {
		List<Product> productList = productRepository.findAll();
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);

	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<Product> getById(@PathVariable Long id) {
		Optional<Product> productOp = productRepository.findById(id);
		return new ResponseEntity<Product>(productOp.get(), HttpStatus.OK);

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductVO productVO) {
		Product product;
		Optional<Product> productOp = productRepository.findById(id);
		if (productOp.isPresent()) {
			product = productOp.get();
			BeanUtils.copyProperties(productVO, product);
			product.setId(id);
			product = productRepository.save(product);
		}
		return new ResponseEntity<Product>(HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Product> update(@PathVariable Long id) {
		productRepository.deleteById(id);
		return new ResponseEntity<Product>(HttpStatus.OK);
	}

	@GetMapping("/getProducts")
	public ResponseEntity<List<ProductVO>> getProducts() {

		SpannerOptions options = SpannerOptions.newBuilder().build();
		Spanner spanner = options.getService();
		String instance = "test-instance";
		String database = "ecart-db";
		List<ProductVO> productList = new ArrayList<>();
		;
		try {
			// Creates a database client
			DatabaseClient dbClient = spanner
					.getDatabaseClient(DatabaseId.of(options.getProjectId(), instance, database));
			// Queries the database
			try (ResultSet resultSet = dbClient.singleUse().executeQuery(Statement.of("SELECT * from product"))) {
				// Prints the results
				while (resultSet.next()) {
					productList.add(new ProductVO(resultSet.getLong("productId"), resultSet.getString("title"),
							resultSet.getString("price"), resultSet.getString("category"),
							resultSet.getString("imageUrl")));
				}
			}
		} finally {
			// Closes the client which will free up the resources used
			spanner.close();
		}

		return new ResponseEntity<List<ProductVO>>(productList, HttpStatus.OK);

	}

	@PostMapping("/createProducts")
	public ResponseEntity<Product> createProducts(@Valid @RequestBody ProductVO productVO) {
		SpannerOptions options = SpannerOptions.newBuilder().build();
		Spanner spanner = options.getService();
		String instance = "test-instance";
		String database = "ecart-db";
		try {
			// Creates a database client
			DatabaseClient dbClient = spanner
					.getDatabaseClient(DatabaseId.of(options.getProjectId(), instance, database));
			// Queries the database
			try {
				dbClient.readWriteTransaction().run(new TransactionCallable<Void>() {
					@Override
					public Void run(TransactionContext transaction) throws Exception {
						String sql = "INSERT INTO product (productId,title,category, imageUrl, price) VALUES " + "(" + 2
								+ ", '" + productVO.getTitle() + "', '" + productVO.getCategory() + "','"
								+ productVO.getImageUrl() + "','" + productVO.getPrice() + "')";
						long rowCount = transaction.executeUpdate(Statement.of(sql));
						System.out.printf("%d records inserted.\n", rowCount);
						return null;
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
			}
		} finally {
			// Closes the client which will free up the resources used
			spanner.close();
		}
		return new ResponseEntity<Product>(HttpStatus.OK);

	}

}
