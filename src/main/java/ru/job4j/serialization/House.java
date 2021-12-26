package ru.job4j.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.Arrays;

@XmlRootElement(name = "house")
@XmlAccessorType(XmlAccessType.FIELD)
public class House {

    @XmlAttribute
    private boolean built;

    @XmlAttribute
    private int floors;

    @XmlAttribute
    private String series;
    private Roof roof;

    @XmlElementWrapper(name = "properties")
    @XmlElement(name = "property")
    private String[] properties;

    public House() {
    }

    public House(boolean built, int floors, String series, Roof roof, String[] properties) {
        this.built = built;
        this.floors = floors;
        this.series = series;
        this.roof = roof;
        this.properties = properties;
    }

    public boolean isBuilt() {
        return built;
    }

    public int getFloors() {
        return floors;
    }

    public String getSeries() {
        return series;
    }

    public Roof getRoof() {
        return roof;
    }

    public String[] getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "House{"
                + " built=" + built
                + ", floors=" + floors
                + ", series='" + series + '\''
                + ", roof=" + roof
                + ", properties=" + Arrays.toString(properties)
                + '}';
    }

    public static void main(String[] args) throws Exception {
        House house = new House(
                true, 9, "A100",
                new Roof("gray"), new String[] {"p1", "p2"});
        final Gson gson = new GsonBuilder().create();
        String jsonStr = gson.toJson(house);
        System.out.println(jsonStr);
        final House houseFromJson = gson.fromJson(jsonStr, House.class);
        System.out.println(houseFromJson);
        System.out.println("----------");
        JAXBContext context = JAXBContext.newInstance(House.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String xml;
        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(house, writer);
            xml = writer.getBuffer().toString();
            System.out.println(xml);
        }
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(xml)) {
            House result = (House) unmarshaller.unmarshal(reader);
            System.out.println(result);
        }
        System.out.println("----------");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("built", house.isBuilt());
        jsonObject.put("floors", house.getFloors());
        jsonObject.put("series", house.getSeries());
        jsonObject.put("roof", house.getRoof());
        jsonObject.put("properties", house.getProperties());
        System.out.println(jsonObject);
    }
}
