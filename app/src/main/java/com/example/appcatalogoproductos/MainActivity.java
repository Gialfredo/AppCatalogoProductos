package com.example.appcatalogoproductos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView productListView;
    private AdaptadorProducto adapter;
    private List<Producto> productList;
    private Spinner categorySpinner;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuración inicial para manejar el teclado
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Inicializar vistas
        productListView = findViewById(R.id.productListView);
        categorySpinner = findViewById(R.id.categorySpinner);
        searchBar = findViewById(R.id.searchBar);

        // Configuración avanzada del ListView
        productListView.setScrollContainer(true);
        productListView.setNestedScrollingEnabled(true);
        productListView.setSmoothScrollbarEnabled(true);

        // Solución para el scroll táctil
        productListView.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });

        // Crear lista de productos de ejemplo
        productList = new ArrayList<>();
        productList.add(new Producto("iPhone 13", "Teléfonos", 999.99, 4.5f, "https://m.media-amazon.com/images/I/61l9ppRIiqL._SL1500_.jpg"));
        productList.add(new Producto("Samsung Galaxy S21", "Teléfonos", 899.99, 4.2f, "https://m.media-amazon.com/images/I/71cb4T2yAeL._AC_SX679_.jpg"));
        productList.add(new Producto("MacBook Pro", "Laptops", 1499.99, 4.8f, "https://m.media-amazon.com/images/I/61aUBxqc5PL._SL1500_.jpg"));
        productList.add(new Producto("AirPods Pro", "Audífonos", 249.99, 4.3f, "https://m.media-amazon.com/images/I/61SUj2aKoEL._SL1500_.jpg"));
        productList.add(new Producto("Kindle Paperwhite", "Lectores eBook", 139.99, 4.7f, "https://m.media-amazon.com/images/I/71FWKtSIYUL._AC_SL1500_.jpg"));
        productList.add(new Producto("Echo Dot (4ta Gen)", "Asistentes", 49.99, 4.6f, "https://m.media-amazon.com/images/I/71Q9d6N7xkL._SL1500_.jpg"));
        productList.add(new Producto("Fire TV Stick 4K", "Streaming", 39.99, 4.5f, "https://m.media-amazon.com/images/I/51TjJOTfslL._SL1500_.jpg"));
        productList.add(new Producto("Logitech MX Master 3", "Accesorios PC", 99.99, 4.8f, "https://m.media-amazon.com/images/I/614w3LuZTYL._SL1500_.jpg"));
        productList.add(new Producto("Sony WH-1000XM4", "Audífonos", 348.00, 4.8f, "https://m.media-amazon.com/images/I/71o8Q5XJS5L._SL1500_.jpg"));


        // Configurar el adaptador
        adapter = new AdaptadorProducto(this, productList);
        productListView.setAdapter(adapter);

        // Configurar Spinner (Filtro por categoría)
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getCategories()
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        // Filtrar al seleccionar categoría
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                String searchQuery = searchBar.getText().toString();
                filterProducts(searchQuery, selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Configurar el campo de búsqueda
        configureSearchBar();
    }

    private void configureSearchBar() {
        // Buscar productos al escribir
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                String category = categorySpinner.getSelectedItem().toString();
                filterProducts(query, category);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Ocultar teclado al presionar "Enter"
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchBar.getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    private List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("Todos");
        for (Producto product : productList) {
            if (!categories.contains(product.getCategoria())) {
                categories.add(product.getCategoria());
            }
        }
        return categories;
    }

    private void filterProducts(String query, String category) {
        List<Producto> filteredList = new ArrayList<>();
        query = query.toLowerCase().trim();

        for (Producto product : productList) {
            boolean matchesCategory = category.equals("Todos") ||
                    product.getCategoria().equals(category);

            boolean matchesSearch = query.isEmpty() ||
                    product.getNombre().toLowerCase().contains(query) ||
                    product.getCategoria().toLowerCase().contains(query) ||
                    String.valueOf(product.getPrecio()).contains(query);

            if (matchesCategory && matchesSearch) {
                filteredList.add(product);
            }
        }

        adapter = new AdaptadorProducto(this, filteredList);
        productListView.setAdapter(adapter);

        // Mostrar feedback si no hay resultados
        if (filteredList.isEmpty() && (!query.isEmpty() || !category.equals("Todos"))) {
            Toast.makeText(this, "No se encontraron productos", Toast.LENGTH_SHORT).show();
        }
    }
}