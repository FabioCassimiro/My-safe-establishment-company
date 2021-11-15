package br.com.mysafeestablishmentcompany.service;


import br.com.mysafeestablishmentcompany.api.imgur.ImgurClient;
import br.com.mysafeestablishmentcompany.api.imgur.UploadImageResponse;
import br.com.mysafeestablishmentcompany.api.request.CreateProductRequest;
import br.com.mysafeestablishmentcompany.api.response.MessageResponse;
import br.com.mysafeestablishmentcompany.api.response.ProductResponse;
import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.domain.ProductDetails;
import br.com.mysafeestablishmentcompany.exception.ProductNotFoundException;
import br.com.mysafeestablishmentcompany.repository.ProductDetailsRepository;
import br.com.mysafeestablishmentcompany.repository.ProductRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private ProductDetailsRepository productDetailsRepository;
    private ImgurClient imgurClient = new ImgurClient();

    public ProductService(ProductRepository productRepository, ProductDetailsRepository productDetailsRepository) {
        this.productRepository = productRepository;
        this.productDetailsRepository = productDetailsRepository;
    }

    public Product register(Product product) throws Exception {
        Product productDTO = productRepository.save(product);
        if (Objects.isNull(productDTO.getId())) {
            throw new Exception(String.format("Não foi possivel criar o produto %s", product.getName()));
        }
        return product;
    }

    public Product registerProduct(CreateProductRequest productRequest) throws Exception {
        File outputFile = convertBase64ToFile(productRequest.getImageEncoded());
        UploadImageResponse uploadImageResponse = imgurClient.uploadImage(outputFile);
        if (uploadImageResponse.getSuccess().equals("false")) {
            throw new Exception("Não foi possivel salvar a imagem");
        }
        ProductDetails productDetails = new ProductDetails(
                uploadImageResponse.getData().getId(),
                uploadImageResponse.getData().getDeletehash(),
                uploadImageResponse.getData().getLink()
        );
        Product product = productRequest.getProduct();
        product.setProductDetails(productDetails);
        Product productDTO = productRepository.save(product);

        if (Objects.isNull(productDTO.getId())) {
            throw new Exception(String.format("Não foi possivel criar o produto %s", productRequest.getProduct().getName()));
        }
        return productDTO;
    }

    private File convertBase64ToFile(String imageEncoded) throws IOException {
        byte[] byteImage = Base64.getDecoder().decode(imageEncoded);
        File outputFile = new File("img.png");
        FileUtils.writeByteArrayToFile(outputFile, byteImage);
        return outputFile;
    }

    public MessageResponse delete(long productId) throws Exception {
        Product productDTO = productRepository.findProductById(productId);
        if (Objects.isNull(productDTO)) {
            throw new ProductNotFoundException(String.format("Produto %s não encontrado ", productId));
        }
        productRepository.delete(productDTO);
        productDTO = productRepository.findProductById(productId);
        if (Objects.nonNull(productDTO)) {
            throw new Exception(String.format("O produto %s não foi deletado", productDTO.getName()));
        }
        return new MessageResponse("O produto: " + productId + " foi deletado com sucesso!");
    }

    public Product update(Product product) throws Exception {
        Product productDTO = productRepository.findProductById(product.getId());
        if (Objects.isNull(productDTO)) {
            throw new ProductNotFoundException(String.format("Produto %s - %s não foi encontrado ", product.getId(), product.getName()));
        }
        return productRepository.save(product);
    }

    public ArrayList<Product> allProducts() throws Exception {
        ArrayList<Product> products = productRepository.findAll();
        if (Objects.isNull(products)) {
            throw new ProductNotFoundException("Nenhum produto não foi encontrado");
        }
        return products;
    }

    public Product product(Long id) throws ProductNotFoundException {
        Product product = productRepository.findProductById(id);
        if (Objects.isNull(product)) {
            throw new ProductNotFoundException("Produto não encontrado");
        }
        return product;
    }

}
