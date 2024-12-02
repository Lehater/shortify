package shortify.controller;


import shortify.domain.ShortLinkService;
import shortify.domain.UserService;

import java.util.Scanner;
import java.util.UUID;

public class CommandLineController {

    private final ShortLinkService shortLinkService = new ShortLinkService();
    private final UserService userService = new UserService();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в систему управления короткими ссылками!");

        UUID userId = userService.createUser();
        System.out.println("Ваш UUID: " + userId);

        while (true) {
            System.out.println("Введите команду (create, delete, edit, show, exit): ");
            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "create":
                    handleCreate(scanner, userId);
                    break;
                case "delete":
                    handleDelete(scanner, userId);
                    break;
                case "edit":
                    handleEdit(scanner, userId);
                    break;
                case "show":
                    handleShow(scanner);
                    break;
                case "exit":
                    System.out.println("Выход из программы...");
                    return;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }

    private void handleCreate(Scanner scanner, UUID userId) {
        System.out.println("Введите длинный URL: ");
        String originalUrl = scanner.nextLine();
        System.out.println("Введите лимит переходов (или оставьте пустым для использования по умолчанию): ");
        String clickLimitInput = scanner.nextLine();
        Integer clickLimit = clickLimitInput.isEmpty() ? null : Integer.parseInt(clickLimitInput);

        var link = shortLinkService.createShortLink(originalUrl, userId, clickLimit, null);
        System.out.println("Ссылка успешно создана: " + link.getShortUrl());
    }

    private void handleDelete(Scanner scanner, UUID userId) {
        System.out.println("Введите короткую ссылку для удаления: ");
        String shortUrl = scanner.nextLine();
        shortLinkService.deleteShortLink(shortUrl, userId);
    }

    private void handleEdit(Scanner scanner, UUID userId) {
        System.out.println("Введите короткую ссылку для редактирования лимита переходов: ");
        String shortUrl = scanner.nextLine();
        System.out.println("Введите новый лимит переходов: ");
        int newLimit = Integer.parseInt(scanner.nextLine());
        shortLinkService.editClickLimit(shortUrl, userId, newLimit);
    }

    private void handleShow(Scanner scanner) {
        System.out.println("Введите короткую ссылку для просмотра: ");
        String shortUrl = scanner.nextLine();
        String originalUrl = shortLinkService.findOriginalUrl(shortUrl);
        if (originalUrl != null) {
            System.out.println("Оригинальный URL: " + originalUrl);
        } else {
            System.out.println("Ссылка не найдена или истек срок её действия.");
        }
    }
}