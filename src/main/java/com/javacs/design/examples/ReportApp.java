package com.javacs.design.examples;

import java.util.*;

public class ReportApp {

    record Sale(String product, double amount) {}

    static class SalesRepository {
        private final List<Sale> sales = new ArrayList<>();

        void add(Sale... newSales) {
            sales.addAll(Arrays.asList(newSales));
        }

        List<Sale> findAll() {
            return List.copyOf(sales);
        }
    }

    static class StatisticsCalculator {
        Map<String, Double> totalByProduct(List<Sale> sales) {
            Map<String, Double> totals = new TreeMap<>();
            for (Sale sale : sales) {
                totals.merge(sale.product(), sale.amount(), Double::sum);
            }
            return totals;
        }
    }

    static class HtmlFormatter {
        String format(Map<String, Double> totals) {
            StringBuilder html = new StringBuilder("<html><body><h1>Monthly Report</h1><ul>");
            totals.forEach((product, total) ->
                    html.append("<li>").append(product).append(": ").append(total).append("</li>"));
            return html.append("</ul></body></html>").toString();
        }
    }

    static class ReportWriter {
        void write(String fileName, String content) {
            System.out.println("Writing " + fileName + " ... " + content.length() + " chars");
        }
    }

    static class EmailSender {
        void send(String recipientEmail, String fileName) {
            System.out.println("Emailing " + fileName + " to " + recipientEmail);
        }
    }

    static class ReportService {
        private final SalesRepository repository;
        private final StatisticsCalculator calculator = new StatisticsCalculator();
        private final HtmlFormatter formatter = new HtmlFormatter();
        private final ReportWriter writer = new ReportWriter();
        private final EmailSender sender = new EmailSender();

        ReportService(SalesRepository repository) {
            this.repository = Objects.requireNonNull(repository);
        }

        void generateAndSend(String recipientEmail) {
            Map<String, Double> totals = calculator.totalByProduct(repository.findAll());
            String html = formatter.format(totals);
            writer.write("report.html", html);
            sender.send(recipientEmail, "report.html");
        }
    }

    public static void main(String[] args) {
        SalesRepository repository = new SalesRepository();
        repository.add(new Sale("Laptop", 1200), new Sale("Phone", 800), new Sale("Laptop", 1100));

        ReportService service = new ReportService(repository);
        service.generateAndSend("boss@example.com");
        service.generateAndSend("boss@example.com");
    }
}