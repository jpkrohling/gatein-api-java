package org.gatein.api.composition;

import org.gatein.api.Portal;
import org.gatein.api.application.Application;
import org.gatein.api.page.Page;
import org.gatein.api.security.Permission;

/**
 *
 * A starting point of the Page Composition API. An instance is typically obtained from {@link Portal}:
 * <pre>
 *     Portal myPortal = PortalRequest.getInstance().getPortal();
 *     PageBuilder myPageBuilder = myPortal.newPageBuilder();
 * </pre>
 *
 * <p>
 * There are two main kinds of possible scenarios:
 * <ol>
 * <li>
 *      <strong>Flat</strong>
 *      <pre>
 *      Page myPage = myPageBuilder
 *              .child(gadgetCalculator) // first row
 *              .child(helloWorldPortlet) // second row
 *              .siteName(siteName)
 *              .siteType(siteType)
 *              .name("myFlatPage")
 *              .build(); // creates a new Page
 *      </pre>
 * </li>
 * <li>
 *      <strong>With nested containers</strong>
 *      <pre>
 *       Page page = pageBuilder
 *        .newRowsBuilder() // we three rows
 *            .newColumnsBuilder() // third column set, on the third row
 *                .child(portletUsefulLinks) // about 1/3 of the width of the third row on the screen
 *                .child(portletUsefulLinks) // same as above
 *                .newColumnsBuilder() // a column set inside this column set: the whole set is 1/3 of the total width
 *                    .child(gadgetCalculator) // and this is 50% of the 1/3
 *                    .child(gadgetRss) // same as above
 *                .buildToParentBuilder() // finishes work on the nested column set
 *            .buildToParentBuilder() // finishes work on the third column set
 *        .buildToTopBuilder()
 *        .siteName("classic") // to which site should this page belong to?
 *        .siteType("portal")
 *        .name("awesome_" + UUID.randomUUID().toString()) // what would be the short name of the page?
 *        .displayName("Awesome page") // and how should be the display name?
 *        .build(); // finishes building the Page object
 *
 *        portal.savePage(page); // and finally, persist the page!
 *      </pre>
 *      See also {@link ContainerBuilder#newColumnsBuilder()}, {@link ContainerBuilder#newRowsBuilder()} and {@link ContainerBuilder#newCustomContainerBuilder(Container)}.
 * </li>
 * </ol>
 *
 * Note that the resulting {@link Page} object needs to be persisted using the
 * {@link org.gatein.api.Portal#savePage(Page)} method.
 * <p>
 * There are more examples available in the Portal API Chapter of Portal Developer Guide and in Page Composition API Quickstart.
 *
 * @see org.gatein.api.Portal#newPageBuilder()
 * @see org.gatein.api.Portal#savePage(Page)
 *
 * @author <a href="mailto:jpkroehling+javadoc@redhat.com">Juraci Paixão Kröhling</a>
 */
public interface PageBuilder extends LayoutBuilder<PageBuilder> {

    /**
     * Optionally sets a description for this page.
     * @param description the description to set
     * @return this builder
     */
    PageBuilder description(String description);

    /**
     * Required: Sets the site name to which this page belongs to
     * @param siteName the site name
     * @return this builder
     */
    PageBuilder siteName(String siteName);

    /**
     * Optionally sets the display name of this page.
     *
     * @param displayName the display name
     * @return this builder
     */
    PageBuilder displayName(String displayName);

    /**
     * Optionally sets whether this page should be shown in the "maximized window" mode.
     *
     * @param showMaxWindow the boolean indicating if the option should be shown
     * @return this builder
     */
    PageBuilder showMaxWindow(boolean showMaxWindow);

    /**
     * Optionally sets the permission object that represents which users will be allowed to access the {@link Page} being built.
     * <p>
     * Unless set explicitly, the default value {@link Container#DEFAULT_ACCESS_PERMISSION} will be used for
     * the resulting {@link Page}.
     *
     * @param accessPermission the access permission for this page
     * @return this builder
     */
    PageBuilder accessPermission(Permission accessPermission);

    /**
     * Optionally sets the permission object that represents which users will be allowed to edit the {@link Page} being built.
     * <p>
     * Unless set explicitly, the default value {@link Page#DEFAULT_EDIT_PERMISSION} will be used for
     * the resulting {@link Page}.
     *
     * @param editPermission the edit permission
     * @return this builder
     */
    PageBuilder editPermission(Permission editPermission);

    /**
     * Optionally sets the permission object that represents which users will be allowed to perform move, add
     * and remove operations with child {@link Application}s of the {@link Page} being built.
     * <p>
     * Unless set explicitly, the default value {@link Container#DEFAULT_MOVE_APPS_PERMISSION} will be used for
     * the resulting {@link Page}.
     *
     * @param moveAppsPermission the list of move apps permissions for this page
     * @return this builder
     */
    PageBuilder moveAppsPermission(Permission moveAppsPermission);

    /**
     * Optionally sets the permission object that represents which users will be allowed to perform move, add
     * and remove operations with child {@link Container}s of the {@link Page} being built.
     * <p>
     * Unless set explicitly, the default value {@link Container#DEFAULT_MOVE_CONTAINERS_PERMISSION} will be used for
     * the resulting {@link Page}.
     *
     * @param moveContainersPermission the list of move containers permissions for this page
     * @return this builder
     */
    PageBuilder moveContainersPermission(Permission moveContainersPermission);

    /**
     * Required: The site type of which this page belongs to. Possible values are:
     * <ul>
     *     <li>portal</li>
     *     <li>site</li>
     *     <li>user</li>
     * </ul>
     *
     * @param siteType    the site type, based on the possible values
     * @return this builder
     * @throws java.lang.IllegalArgumentException if the provided value is none of the possible values
     */
    PageBuilder siteType(String siteType);

    /**
     * Builds a new {@link Page} based on the provided information.
     * <p>
     * This method can be safely called multiple times on the same {@link ContainerBuilder} instance,
     * e.g. to produce {@link Page}s that differ in some small detail:
     * <pre>
     * PageBuilder myPageBuilder = ...
     * Page p1 = myPageBuilder
     *      .child(ch1)
     *      .child(ch2)
     *      .siteName(siteName)
     *      .siteType(siteType)
     *      .name("page1")
     *      .build();
     *
     * Page p2 = myPageBuilder
     *      // children and site information already set
     *      .name("page2") // change the name
     *      .build();
     *
     * // p1 and p2 differ only in name.
     *<p>
     * @return a new {@link Page} object
     * @throws java.lang.IllegalStateException if any mandatory information is not provided
     */
    public Page build();

    /**
     * Required: The page name
     *
     * @param name    the page name
     * @return this builder
     */
    public PageBuilder name(String name);

}
