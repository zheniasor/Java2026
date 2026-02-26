package com.example.transmittingnumber.controller;

import com.example.transmittingnumber.dao.PhoneBookDAO;
import com.example.transmittingnumber.entity.PhoneEntry;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/phonebook")
public class PhoneBookServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(PhoneBookServlet.class);
    private PhoneBookDAO phoneBookDAO = new PhoneBookDAO();

    @Override
    public void init() {
        LOGGER.info("Инициализация PhoneBookServlet");
        phoneBookDAO.createTable();
        LOGGER.info("PhoneBookServlet инициализирован");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("Получен POST запрос");

        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");

        LOGGER.info("Получены данные: фамилия={}, телефон={}", lastName, phoneNumber);

        if (lastName == null || phoneNumber == null ||
                lastName.trim().isEmpty() || phoneNumber.trim().isEmpty()) {
            LOGGER.warn("Пустые поля в форме");
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=empty");
            return;
        }

        if (!phoneNumber.matches("[0-9-]+")) {
            LOGGER.warn("Неверный формат номера: {}", phoneNumber);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Номер должен содержать только цифры и дефисы");
            return;
        }

        PhoneEntry entry = new PhoneEntry(lastName.trim(), phoneNumber.trim());
        LOGGER.info("Добавление записи: {}", entry.getLastName());

        boolean added = phoneBookDAO.addEntry(entry);

        if (added) {
            LOGGER.info("Запись успешно добавлена в БД");
            response.sendRedirect(request.getContextPath() + "/phonebook?action=list");
        } else {
            LOGGER.error("Ошибка при добавлении записи в БД");
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=db");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("Получен GET запрос");

        String action = request.getParameter("action");
        LOGGER.info("Параметр action: {}", action);

        if ("list".equals(action)) {
            LOGGER.info("Запрос списка всех записей");
            List<PhoneEntry> entries = phoneBookDAO.getAllEntries();
            LOGGER.info("Загружено записей: {}", entries.size());

            request.setAttribute("entries", entries);
            request.getRequestDispatcher("/pages/phonebook.jsp").forward(request, response);
            LOGGER.info("Страница phonebook.jsp отображена");
        } else {
            LOGGER.info("Перенаправление на index.jsp");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}