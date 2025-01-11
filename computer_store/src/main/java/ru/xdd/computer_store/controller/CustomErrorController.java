//package ru.xdd.computer_store.controller;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class CustomErrorController {
//
//    @RequestMapping("/custom-error")
//    public String handleError(HttpServletRequest request, Model model) {
//        Object status = request.getAttribute("javax.servlet.error.status_code");
//        String errorMessage = "Произошла ошибка";
//
//        if (status != null) {
//            int statusCode = Integer.parseInt(status.toString());
//            switch (statusCode) {
//                case 403:
//                    errorMessage = "Доступ запрещён";
//                    break;
//                case 404:
//                    errorMessage = "Страница не найдена";
//                    break;
//                case 500:
//                    errorMessage = "Внутренняя ошибка сервера";
//                    break;
//                default:
//                    errorMessage = "Неизвестная ошибка";
//                    break;
//            }
//        }
//
//        model.addAttribute("errorMessage", errorMessage);
//        return "error";
//    }
//}
//
