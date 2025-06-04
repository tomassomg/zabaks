package jtt.locmelis.dto;

public class Shoes {
    private int id;
    private String name;
    private String brand;
    private double price;
    private String gender;
    private String sizes;
    private String color;
    private String url;

    public Shoes() {}
    
    public Shoes(int id, String name, String brand, double price, String gender, String sizes, String color, String url) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.gender = gender;
        this.sizes = sizes;
        this.color = color;
        this.url = url;   }

//    public Shoes(String name, String brand, double price, String gender, String sizes, String color) {
//        this(0, name, brand, price, gender, sizes, color);
//    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public String getGender() { return gender; }
    public String getSizes() { return sizes; }
    public String getColor() { return color; }
    public String getUrl() { return url; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setPrice(double price) { this.price = price; }
    public void setGender(String gender) { this.gender = gender; }
    public void setSizes(String sizes) { this.sizes = sizes; }
    public void setColor(String color) { this.color = color; }
    public void setUrl(String url) { this.url = url; }
}
