package cn.withwang.cs.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuItem {
    private String name;
    private String key;

    private String path;
    private String component;
    private String icon;
    private List<MenuItem> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return this.key;
    }

    public String getPath() {
        return this.path;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return this.component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuItem> getChildren() {
        return this.children;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(name, menuItem.name) &&
                Objects.equals(key, menuItem.key) &&
                Objects.equals(path, menuItem.path) &&
                Objects.equals(component, menuItem.component) &&
                Objects.equals(icon, menuItem.icon) &&
                Objects.equals(children, menuItem.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, key, path, component, icon, children);
    }

}