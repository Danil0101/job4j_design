package ru.job4j.io;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ConfigTest {
    @Test
    public void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Petr Arsentev"));
        assertThat(config.value("surname"), is(nullValue()));
    }

    @Test
    public void whenPairWithCommentsAndWhitespaces() {
        String path = "./data/pair_with_comments_and_whitespaces.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("testKey"), is("testValue"));
        assertThat(config.value("comment"), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenIllegalArgumentException() {
        String path = "./data/pair_with_comments_and_whitespaces.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value(""), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNoKey() {
        String path = "./data/no_key.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNoValue() {
        String path = "./data/no_value.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNoEqualSign() {
        String path = "./data/no_equal_sign.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoreThanOneSignEquals() {
        String path = "./data/more_than_one_sign_equals.properties";
        Config config = new Config(path);
        config.load();
    }
}