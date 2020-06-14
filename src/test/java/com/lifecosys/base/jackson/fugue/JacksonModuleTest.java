package com.lifecosys.base.jackson.fugue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.atlassian.fugue.Either;
import io.atlassian.fugue.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.atlassian.fugue.Option.option;
import static java.util.Arrays.asList;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
public class JacksonModuleTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void beforeTest() {
        objectMapper.registerModules(new Jdk8Module(), new FugueJacksonModule());
    }

    @Test
    public void simpleJsonTest() throws IOException {

        Assertions.assertEquals("\"test json\"", objectMapper.writeValueAsString(Optional.of("test json")));
        Assertions.assertEquals("\"test json\"", objectMapper.writeValueAsString(option("test json")));

        Assertions.assertEquals(Optional.of("test json"), objectMapper.readValue("\"test json\"", Optional.class));
        Assertions.assertEquals(option("test json"), objectMapper.readValue("\"test json\"", Option.class));
    }

    @Test
    public void pojoJsonTest() throws IOException {
        SimplePojo simplePojo = new SimplePojo("test name", option("test address"), option(100));
        String json=objectMapper.writeValueAsString(option(simplePojo));
        Assertions.assertEquals(simplePojo, objectMapper.readValue(json, SimplePojo.class));
    }
    @Test
    public void complexJsonTest() throws IOException {
        SimplePojo simplePojo = new SimplePojo("test name", option("test address"), option(100));
        SimplePojo simplePojo2 = new SimplePojo("test name2", option("test address2"), option(100));
        ComplexPojo complexPojo = new ComplexPojo("test name", option("test address"), option(simplePojo), asList(option(simplePojo), option(simplePojo2)));
        String json=objectMapper.writeValueAsString(complexPojo);
        System.out.println(json);
        Assertions.assertEquals(complexPojo, objectMapper.readValue(json, ComplexPojo.class));
    }


    @Test
    public void eitherSimpleJsonSerializerTest() throws IOException {
        Either<String,String> right=Either.right("test right");
        Either<String,String> left=Either.left("test left");
        Assertions.assertEquals("\"test right\"", objectMapper.writeValueAsString(right));
        Assertions.assertEquals("\"test left\"", objectMapper.writeValueAsString(left));

    }
    @Test
    public void eitherPojoJsonSerializerTest() throws IOException {
        SimplePojo simplePojo = new SimplePojo("test name", option("test address"), option(100));
        Either<String,SimplePojo> right=Either.right(simplePojo);
        Either<String,SimplePojo> left=Either.left("test error");
        Assertions.assertEquals(objectMapper.writeValueAsString(simplePojo), objectMapper.writeValueAsString(right));
        Assertions.assertEquals("\"test error\"", objectMapper.writeValueAsString(left));
    }

}

class ComplexPojo {
    private String name;
    private Option<String> address;
    private Option<SimplePojo> simplePojo;
    private List<Option<SimplePojo>> simplePojos;

    public ComplexPojo() {
    }

    public ComplexPojo(String name, Option<String> address, Option<SimplePojo> simplePojo,List<Option<SimplePojo>> simplePojos) {
        this.name = name;
        this.address = address;
        this.simplePojo = simplePojo;
        this.simplePojos = simplePojos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Option<String> getAddress() {
        return address;
    }

    public void setAddress(Option<String> address) {
        this.address = address;
    }

    public Option<SimplePojo> getSimplePojo() {
        return simplePojo;
    }

    public void setSimplePojo(Option<SimplePojo> simplePojo) {
        this.simplePojo = simplePojo;
    }

    public List<Option<SimplePojo>> getSimplePojos() {
        return simplePojos;
    }

    public void setSimplePojos(List<Option<SimplePojo>> simplePojos) {
        this.simplePojos = simplePojos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexPojo that = (ComplexPojo) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(simplePojo, that.simplePojo) &&
                Objects.equals(simplePojos, that.simplePojos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, simplePojo, simplePojos);
    }
}

class SimplePojo {
    private String name;
    private Option<String> address;
    private Option<Integer> age;

    public SimplePojo() {
    }

    public SimplePojo(String name, Option<String> address, Option<Integer> age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Option<String> getAddress() {
        return address;
    }

    public void setAddress(Option<String> address) {
        this.address = address;
    }

    public Option<Integer> getAge() {
        return age;
    }

    public void setAge(Option<Integer> age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplePojo that = (SimplePojo) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, age);
    }
}
