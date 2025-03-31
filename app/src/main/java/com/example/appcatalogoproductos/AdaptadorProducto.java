package com.example.appcatalogoproductos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorProducto extends ArrayAdapter<Producto> {
    private List<Producto> productList;

    public AdaptadorProducto(Context context, List<Producto> products) {
        super(context, 0, products);
        this.productList = products;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.articulo_producto, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Producto product = getItem(position);
        if (product != null) {
            holder.bind(product);
        }

        return convertView;
    }

    static class ViewHolder {
        private final ImageView imagen;
        private final TextView nombre;
        private final TextView precio;
        private final RatingBar rating;

        ViewHolder(View view) {
            imagen = view.findViewById(R.id.imagenProducto);
            nombre = view.findViewById(R.id.nombreProducto);
            precio = view.findViewById(R.id.precioProducto);
            rating = view.findViewById(R.id.calificacionProducto);
        }

        void bind(Producto product) {
            nombre.setText(product.getNombre());
            precio.setText(String.format("$%.2f", product.getPrecio()));
            rating.setRating(product.getCalificacion());
            Picasso.get().load(product.getImagenUrl()).into(imagen);
        }
    }

    @Override
    public int getCount() {
        return productList != null ? productList.size() : 0;
    }

    @Override
    public Producto getItem(int position) {
        return productList.get(position);
    }
}