package model;

import lombok.Data;

@Data
public class GraphQlQuery {
    private String query;
    private Object variables;
}