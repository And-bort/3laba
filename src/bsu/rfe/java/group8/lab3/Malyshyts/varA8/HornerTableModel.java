package bsu.rfe.java.group8.lab3.Malyshyts.varA8;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
class HornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public HornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.setCoefficients(coefficients);
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
        return 3; // Три столбца
    }

    public int getRowCount() {
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
        double x = from + step * row;
        double hornerValue = calculateHorner(x);
        hornerValue = Math.round(hornerValue * 100.0) / 100.0; // Округляем до двух знаков после запятой
        return switch (col) {
            case 0 -> x;
            case 1 -> hornerValue;
            case 2 -> isOddFractionalPart(hornerValue);
            default -> null;
        };
    }

    public boolean isOddFractionalPart(double value) {
        double fractionalPart = value - Math.floor(value); // Получаем дробную часть
        int twoDigitNumber = (int) (fractionalPart * 100); // Преобразуем дробную часть в двухзначное число
        return twoDigitNumber % 2 != 0; // Проверяем, является ли это число нечётным
    }

    public String getColumnName(int col) {
        return switch (col) {
            case 0 -> "Значение X";
            case 1 -> "Значение многочлена";
            case 2 -> "Дробная часть нечётная?";
            default -> "";
        };
    }

    public Class<?> getColumnClass(int col) {
        return switch (col) {
            case 2 -> Boolean.class; // Булевское значение для третьего столбца
            case 0, 1 -> Double.class;
            default -> Object.class;
        };
    }

    private double calculateHorner(double x) {
        Double b = coefficients[coefficients.length - 1];
        for (int i = coefficients.length - 2; i >= 0; i--) {
            b = b * x + coefficients[i];
        }
        return b;
    }

    public Double[] getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(Double[] coefficients) {
        this.coefficients = coefficients;
    }
}