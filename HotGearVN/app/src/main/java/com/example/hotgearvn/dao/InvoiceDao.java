package com.example.hotgearvn.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.model.InvoiceWithProducts;

import java.util.List;

@Dao
public interface InvoiceDao {
    @Insert(onConflict = REPLACE)
    void add(Invoice invoice);

    @Insert(onConflict = REPLACE)
    void addInvoices(Invoice...invoices);

    @Delete
    void delete(Invoice invoice);

    @Query("SELECT * FROM invoice")
    List<Invoice> getAll();

    @Insert()
    void addAll(Invoice[] invoices);

    @Query("SELECT * FROM invoice WHERE invoice_id = :id")
    Invoice getById(Long id);

    @Query("SELECT i.* FROM users u join invoice i on u.user_id = i.user_id WHERE u.user_id= :id")
    List<Invoice> getHistoryById(Long id);

}
