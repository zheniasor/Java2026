package com.example.transmittingnumber.dao;

import com.example.transmittingnumber.entity.PhoneEntry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhoneBookDAO {
    private static final Logger LOGGER = LogManager.getLogger(PhoneBookDAO.class);
    private static final String URL = "jdbc:mysql://localhost:3306/phonebook";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.info("MySQL JDBC Driver загружен");
        } catch (ClassNotFoundException e) {
            LOGGER.error("MySQL JDBC Driver не найден", e);
        }
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS phone_entries (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "last_name VARCHAR(100) NOT NULL, " +
                "phone_number VARCHAR(20) NOT NULL " +
                ")";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            LOGGER.info("Таблица создана или уже существует");
        } catch (SQLException e) {
            LOGGER.error("Ошибка при создании таблицы", e);
        }
    }

    public boolean addEntry(PhoneEntry entry) {
        String sql = "INSERT INTO phone_entries (last_name, phone_number) VALUES (?, ?)";
        LOGGER.info("Попытка добавить запись: {}, {}", entry.getLastName(), entry.getPhoneNumber());

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entry.getLastName());
            pstmt.setString(2, entry.getPhoneNumber());
            pstmt.executeUpdate();
            LOGGER.info("Запись успешно добавлена");
            return true;
        } catch (SQLException e) {
            LOGGER.error("Ошибка при добавлении записи", e);
            return false;
        }
    }

    public List<PhoneEntry> getAllEntries() {
        List<PhoneEntry> entries = new ArrayList<>();
        String sql = "SELECT * FROM phone_entries ORDER BY id ASC";
        LOGGER.info("Запрос всех записей из БД");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PhoneEntry entry = new PhoneEntry();
                entry.setId(rs.getInt("id"));
                entry.setLastName(rs.getString("last_name"));
                entry.setPhoneNumber(rs.getString("phone_number"));
                entries.add(entry);
            }
            LOGGER.info("Загружено записей: {}", entries.size());
        } catch (SQLException e) {
            LOGGER.error("Ошибка при загрузке записей", e);
        }
        return entries;
    }

    public PhoneEntry getByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM phone_entries WHERE phone_number = ?";
        LOGGER.info("Поиск записи по номеру: {}", phoneNumber);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, phoneNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                PhoneEntry entry = new PhoneEntry();
                entry.setId(rs.getInt("id"));
                entry.setLastName(rs.getString("last_name"));
                entry.setPhoneNumber(rs.getString("phone_number"));
                LOGGER.info("Запись найдена");
                return entry;
            } else {
                LOGGER.info("Запись не найдена");
            }

        } catch (SQLException e) {
            LOGGER.error("Ошибка при поиске записи", e);
        }
        return null;
    }

    public void deleteEntry(int id) {
        String sql = "DELETE FROM phone_entries WHERE id = ?";
        LOGGER.info("Удаление записи с id: {}", id);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            LOGGER.info("Запись удалена");
        } catch (SQLException e) {
            LOGGER.error("Ошибка при удалении записи", e);
        }
    }
}