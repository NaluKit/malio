package com.github.nalukit.malio.shared;

public class Malio {

  public static String getVersion() {
    // TODO Change this for other versions
    return "HEAD-SNAPSHOT";
  }

//  public static boolean hasHistory() {
//    return PropertyFactory.get()
//                          .hasHistory();
//  }
//
//  public static boolean isUsingHash() {
//    return PropertyFactory.get()
//                          .isUsingHash();
//  }
//
//  public static boolean isUsingColonForParametersInUrl() {
//    return PropertyFactory.get()
//                          .isUsingColonForParametersInUrl();
//  }
//
//  /**
//   * This method compares the route with the value of withRoute respecting parameters.
//   *
//   * <ul>
//   * <li>the route can contain parameter values</li>
//   * <li>the withRoute should contain '*' instead of parameter values</li>
//   * </ul>
//   * <p>
//   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;edit'
//   * will return true
//   * <p>
//   * Comparing route '/app/person/3/edit/ with '/app/person/edit/*&#47;'
//   * will return false.
//   * <p>
//   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;/*&#47;'
//   * will return true.
//   *
//   * @param route     the route containing parameter values instead of '*'
//   * @param withRoute the compare route which has no parameter values and uses '*' instead
//   * @return true the routes matches or false in case not
//   */
//  public static boolean match(String route,
//                              String withRoute) {
//    return RouterUtils.get()
//                      .match(route,
//                             withRoute);
//  }
//
//  /**
//   * This method compares the route with the value of withRoute respecting parameters.
//   *
//   * <ul>
//   * <li>the route can contain parameter values</li>
//   * <li>the withRoute should contain '*' instead of parameter values</li>
//   * </ul>
//   * <p>
//   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;edit'
//   * will return true
//   * <p>
//   * Comparing route '/app/person/3/edit/ with '/app/person/edit/*&#47;'
//   * will return false.
//   * <p>
//   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;/*&#47;'
//   * will return true.
//   *
//   * @param route     the route containing parameter values instead of '*'
//   * @param withRoute the compare route which has no parameter values and uses '*' instead
//   * @param exact     if true, routes must match exactly
//   * @return true the routes matches or false in case not
//   */
//  public static boolean match(String route,
//                              String withRoute,
//                              boolean exact) {
//    return RouterUtils.get()
//                      .match(route,
//                             withRoute,
//                             exact);
//  }

}
