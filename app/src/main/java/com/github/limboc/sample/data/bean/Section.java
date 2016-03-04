package com.github.limboc.sample.data.bean;

import java.lang.*;
import java.util.List;


public class Section {
    private String name;
    private List<Character> characters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
