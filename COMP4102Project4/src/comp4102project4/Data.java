package comp4102project4;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Data {

    private StringProperty data1;
    private StringProperty data2;
    private StringProperty data3;
    private StringProperty data4;
    private StringProperty data5;

    public Data(String data1, String data2, String data3, String data4, String data5) {
        setData1(data1);
        setData2(data2);
        setData3(data3);
        setData4(data4);
        setData5(data5);

    }

    public String getData1() {
        return data1Property().get();
    }

    public void setData1(String data1) {
        data1Property().set(data1);
    }

    public StringProperty data1Property() {
        if (data1 == null) {
            data1 = new SimpleStringProperty(this, "data1");
        }
        return data1;
    }

    public String getData2() {
        return data2Property().get();
    }

    public void setData2(String data2) {
        data2Property().set(data2);
    }

    public StringProperty data2Property() {
        if (data2 == null) {
            data2 = new SimpleStringProperty(this, "data2");
        }
        return data2;
    }

    public String getData3() {
        return data3Property().get();
    }

    public void setData3(String data3) {
        data3Property().set(data3);
    }

    public StringProperty data3Property() {
        if (data3 == null) {
            data3 = new SimpleStringProperty(this, "data3");
        }
        return data3;
    }

    public String getData4() {
        return data4Property().get();
    }

    public void setData4(String data4) {
        data4Property().set(data4);
    }

    public StringProperty data4Property() {
        if (data4 == null) {
            data4 = new SimpleStringProperty(this, "data4");
        }
        return data4;
    }

    public String getData5() {
        return data5Property().get();
    }

    public void setData5(String data5) {
        data5Property().set(data5);
    }

    public StringProperty data5Property() {
        if (data5 == null) {
            data5 = new SimpleStringProperty(this, "data5");
        }
        return data5;
    }
}
