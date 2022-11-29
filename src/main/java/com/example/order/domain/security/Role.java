package com.example.order.domain.security;

import java.util.List;

public enum Role {
    USER(Feature.ORDER_ITEM, Feature.VIEW_ORDERS, Feature.REORDER, Feature.ADD_TO_SHOPPING),
    ADMIN(Feature.ADD_ITEM, Feature.UPDATE_ITEM, Feature.VIEW_ALL_USERS, Feature.VIEW_SINGLE_USER,
            Feature.VIEW_TODAY_ORDERS, Feature.VIEW_ORDER_URGENCY);

    private final List<Feature> features;
    Role(Feature... features) {this.features = List.of(features);}

    public boolean hasFeature(Feature feature) {return this.features.contains(feature);}
}
