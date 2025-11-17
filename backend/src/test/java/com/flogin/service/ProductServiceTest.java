package com.flogin.service;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Service Unit Tests")
class ProductServiceTest {

    private ProductService service;

    @BeforeEach
    void setUp() {
        Product p = new Product();
        service = new ProductService();
        service.reset(); // reset dữ liệu trước mỗi test
    }

    // ===== CREATE =====
    @Test
    @DisplayName("TC1: Tao san pham moi thanh cong")
    void testCreateProduct() {
        Product p = new Product("Laptop", 1000, 5);
        Product created = service.createProduct(p);
        assertNotNull(created);
        assertEquals(1, created.getId());
        assertEquals("Laptop", created.getName());
    }

    // ===== GET =====
    @Test
    @DisplayName("TC2: Lay san pham theo id thanh cong")
    void testGetProduct() {
        Product p = service.createProduct(new Product("Phone", 500, 10));
        Product fetched = service.getProduct(p.getId());
        assertNotNull(fetched);
        assertEquals("Phone", fetched.getName());
    }

    @Test
    @DisplayName("TC3: Lay san pham voi id khong ton tai")
    void testGetProductNotFound() {
        Product fetched = service.getProduct(999);
        assertNull(fetched);
    }

    // ===== UPDATE =====
    @Test
    @DisplayName("TC4: Cap nhat san pham thanh cong")
    void testUpdateProduct() {
        Product p = service.createProduct(new Product("Tablet", 300, 3));
        Product updated = new Product("Tablet Pro", 350, 4);
        Product result = service.updateProduct(p.getId(), updated);
        assertNotNull(result);
        assertEquals("Tablet Pro", result.getName());
        assertEquals(350, result.getPrice());
        assertEquals(4, result.getQuantity());
    }

    @Test
    @DisplayName("TC5: Cap nhat san pham voi id khong ton tai")
    void testUpdateProductNotFound() {
        Product updated = new Product("NonExist", 100, 1);
        Product result = service.updateProduct(999, updated);
        assertNull(result);
    }

    // ===== DELETE =====
    @Test
    @DisplayName("TC6: Xoa san pham thanh cong")
    void testDeleteProduct() {
        Product p = service.createProduct(new Product("Camera", 700, 2));
        boolean deleted = service.deleteProduct(p.getId());
        assertTrue(deleted);
        assertNull(service.getProduct(p.getId()));
    }

    @Test
    @DisplayName("TC7: Xoa san pham voi id khong ton tai")
    void testDeleteProductNotFound() {
        boolean deleted = service.deleteProduct(999);
        assertFalse(deleted);
    }

    // ===== GET ALL WITH PAGINATION =====
    @Test
    @DisplayName("TC8: Lay tat ca san pham voi phan trang")
    void testGetAllWithPagination() {
        for (int i = 1; i <= 25; i++) {
            service.createProduct(new Product("P" + i, i * 10, i));
        }

        List<Product> page1 = service.getAll(1, 10);
        List<Product> page2 = service.getAll(2, 10);
        List<Product> page3 = service.getAll(3, 10);

        assertEquals(10, page1.size());
        assertEquals("P1", page1.get(0).getName());

        assertEquals(10, page2.size());
        assertEquals("P11", page2.get(0).getName());

        assertEquals(5, page3.size());
        assertEquals("P21", page3.get(0).getName());
    }
}
