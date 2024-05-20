package com.example.travailafaire.web;

import com.example.travailafaire.DAO.entities.Product;
import com.example.travailafaire.service.ProductManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Controller
public class ProductController {

    @Autowired
    private ProductManager productManager;

    @GetMapping("/products")
    public String viewProducts(Model model,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "taille", defaultValue = "6") int taille,
                               @RequestParam(name = "userId") Long userId) {
        Page<Product> productsPage = productManager.getAllProducts(page, taille); // Fetch products for the specified page and size
        List<Product> products = productsPage.getContent(); // Extract products from the page
        model.addAttribute("products", products);
        model.addAttribute("userId", userId); // Add the user ID to the model
        return "listProduct"; // Return the view to display the list of products
    }

    @GetMapping("/review")
    public String Review() {
        return "Review";
    }

    @GetMapping("/ourbrands")
    public String OurBrand() {
        return "OurBrands";
    }


    @GetMapping("/home")
    public String home() {
        return "home"; // Ceci renvoie au fichier HTML home.html dans le dossier des ressources
    }
    private void populateModel(Model model, int page, int taille, String keyword) {
        Page<Product> products;
        if (keyword.isEmpty()) {
            products = productManager.getAllProducts(page, taille);
        } else {
            products = productManager.searchProductsByKeyword(keyword, page, taille);
        }
        model.addAttribute("listProduct", products.getContent());
        model.addAttribute("pages", new int[products.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("keyword", keyword);
    }

    // Méthode pour ajouter un produit depuis la page "listProduct"
    @PostMapping("/ProductList/ajouterproduit-db")
    public String addToCart(/* Paramètres d'ajout de produit */) {
        // Ajout du produit
        // Redirection vers la page "listProduct" après ajout
        return "redirect:/listProduct";
    }
    @GetMapping("/Index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "taille", defaultValue = "6") int taille,
                        @RequestParam(name = "search", defaultValue = "") String keyword) {
        populateModel(model, page, taille, keyword);
        return "Index";
    }

    // Méthode pour afficher la page "index"


    // Méthode pour afficher la page "listProduct"
    @GetMapping("/ProductList")
    public String listProduct(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "taille", defaultValue = "6") int taille,
                              @RequestParam(name = "search", defaultValue = "") String keyword) {
        // Appel de la méthode pour récupérer les produits à afficher
        populateModel(model, page, taille, keyword);
        // Retourne la vue de la page "listProduct"
        return "listProduct";
    }

    @GetMapping("/ajouterproduit")
    public String addProduct(Model model) {
        return "Add";
    }

@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/ajouterproduit-db")
public String addProductDb(Model model, @ModelAttribute @Valid Product product,
                           @RequestParam("image") MultipartFile imageFile,
                           BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        // If there are validation errors, return to the add page with the errors displayed
        return "Add";
    }
    // Check if the image file is not empty
    if (!imageFile.isEmpty()) {
        try {
            String uploadDir = "public/images"; // Directory to save uploaded images
            Path uploadPath = Paths.get(uploadDir);
            // Create the directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            // Get the original filename of the uploaded file
            String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
            // Generate a unique filename to prevent conflicts
            String uniqueFilename = UUID.randomUUID().toString() + "-" + originalFilename;
            // Save the file to the upload directory
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(imageFile.getInputStream(), filePath);
            // Set the image URL in the product object
            product.setImageUrl("/images/" + uniqueFilename);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to upload image.");
            return "Add";
        }
    }
    System.out.println("done");
    // Save the product in the database
    productManager.addProduct(product);
    // Redirect to the product list page after successful addition
    return "redirect:/ProductList";
}


    @PostMapping("/addOnce")
    public String ajouterProduit(Model model,
                                 @Valid Product product,
                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "Add" ;
        }
        productManager.addProduct(product);
        return "redirect:ProductList";
    }

    @GetMapping("/editProduct")
    public String editProductAction(Model model, @RequestParam(name = "id") Long id) {
        Product product = productManager.getProductById(id);
        if (product != null) {
            model.addAttribute("productToBeUpdated", product);
            return "Edit";
        } else {
            return "error";
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/updateProduct")
    public String updateProduct(Model model,
                                @RequestParam(name = "id") Long id,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "description") String description,
                                @RequestParam(name = "price") double price,
                                @RequestParam(name = "image", required = false) MultipartFile imageFile) {
        // Retrieve the product to be updated from the database
        Product productToUpdate = productManager.getProductById(id);
        if (productToUpdate == null) {
            model.addAttribute("error", "Product not found.");
            return "error"; // Return an error page if the product does not exist
        }

        // Update name, description, and price
        productToUpdate.setName(name);
        productToUpdate.setDescription(description);
        productToUpdate.setPrice(price);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Save the new image file
                String uploadDir = "public/images"; // Directory to save uploaded images
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                // Get the original filename of the uploaded file
                String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
                // Generate a unique filename to prevent conflicts
                String uniqueFilename = UUID.randomUUID().toString() + "-" + originalFilename;
                // Save the file to the upload directory
                Path filePath = uploadPath.resolve(uniqueFilename);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                // Set the image URL in the product object
                productToUpdate.setImageUrl("/images/" + uniqueFilename);

                // Optional: Delete old image file if it exists
                if (productToUpdate.getImageUrl() != null) {
                    Path oldFilePath = Paths.get(uploadDir, productToUpdate.getImageUrl().substring("/images/".length()));
                    Files.deleteIfExists(oldFilePath);
                }
            } catch (IOException e) {
                // Handle file upload exception
                e.printStackTrace();
                model.addAttribute("error", "Failed to upload image.");
                return "Edit"; // Return to the edit page with an error message
            }
        }

        // Update the product in the database
        productManager.updateProduct(productToUpdate);
        // Redirect to the product list page after successful update
        return "redirect:/ProductList";
    }


    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam(name = "id") Long id) {
        if (productManager.deleteProduct(id)) {
            return "redirect:/ProductList";
        } else {
            return "error";
        }
    }


}
