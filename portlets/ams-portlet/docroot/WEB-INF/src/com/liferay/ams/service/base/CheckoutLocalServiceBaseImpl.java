/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.ams.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.ams.model.Checkout;
import com.liferay.ams.service.CheckoutLocalService;
import com.liferay.ams.service.persistence.AssetPersistence;
import com.liferay.ams.service.persistence.CheckoutPersistence;
import com.liferay.ams.service.persistence.DefinitionPersistence;
import com.liferay.ams.service.persistence.TypePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the checkout local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.ams.service.impl.CheckoutLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.ams.service.impl.CheckoutLocalServiceImpl
 * @see com.liferay.ams.service.CheckoutLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class CheckoutLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements CheckoutLocalService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.ams.service.CheckoutLocalServiceUtil} to access the checkout local service.
	 */

	/**
	 * Adds the checkout to the database. Also notifies the appropriate model listeners.
	 *
	 * @param checkout the checkout
	 * @return the checkout that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Checkout addCheckout(Checkout checkout) {
		checkout.setNew(true);

		return checkoutPersistence.update(checkout);
	}

	/**
	 * Creates a new checkout with the primary key. Does not add the checkout to the database.
	 *
	 * @param checkoutId the primary key for the new checkout
	 * @return the new checkout
	 */
	@Override
	public Checkout createCheckout(long checkoutId) {
		return checkoutPersistence.create(checkoutId);
	}

	/**
	 * Deletes the checkout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param checkoutId the primary key of the checkout
	 * @return the checkout that was removed
	 * @throws PortalException if a checkout with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Checkout deleteCheckout(long checkoutId) throws PortalException {
		return checkoutPersistence.remove(checkoutId);
	}

	/**
	 * Deletes the checkout from the database. Also notifies the appropriate model listeners.
	 *
	 * @param checkout the checkout
	 * @return the checkout that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Checkout deleteCheckout(Checkout checkout) {
		return checkoutPersistence.remove(checkout);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(Checkout.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return checkoutPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.ams.model.impl.CheckoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return checkoutPersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.ams.model.impl.CheckoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return checkoutPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return checkoutPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return checkoutPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public Checkout fetchCheckout(long checkoutId) {
		return checkoutPersistence.fetchByPrimaryKey(checkoutId);
	}

	/**
	 * Returns the checkout with the primary key.
	 *
	 * @param checkoutId the primary key of the checkout
	 * @return the checkout
	 * @throws PortalException if a checkout with the primary key could not be found
	 */
	@Override
	public Checkout getCheckout(long checkoutId) throws PortalException {
		return checkoutPersistence.findByPrimaryKey(checkoutId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.ams.service.CheckoutLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(Checkout.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("checkoutId");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.ams.service.CheckoutLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(Checkout.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("checkoutId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return checkoutLocalService.deleteCheckout((Checkout)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return checkoutPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the checkouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.ams.model.impl.CheckoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of checkouts
	 * @param end the upper bound of the range of checkouts (not inclusive)
	 * @return the range of checkouts
	 */
	@Override
	public List<Checkout> getCheckouts(int start, int end) {
		return checkoutPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of checkouts.
	 *
	 * @return the number of checkouts
	 */
	@Override
	public int getCheckoutsCount() {
		return checkoutPersistence.countAll();
	}

	/**
	 * Updates the checkout in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param checkout the checkout
	 * @return the checkout that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Checkout updateCheckout(Checkout checkout) {
		return checkoutPersistence.update(checkout);
	}

	/**
	 * Returns the asset local service.
	 *
	 * @return the asset local service
	 */
	public com.liferay.ams.service.AssetLocalService getAssetLocalService() {
		return assetLocalService;
	}

	/**
	 * Sets the asset local service.
	 *
	 * @param assetLocalService the asset local service
	 */
	public void setAssetLocalService(
		com.liferay.ams.service.AssetLocalService assetLocalService) {
		this.assetLocalService = assetLocalService;
	}

	/**
	 * Returns the asset persistence.
	 *
	 * @return the asset persistence
	 */
	public AssetPersistence getAssetPersistence() {
		return assetPersistence;
	}

	/**
	 * Sets the asset persistence.
	 *
	 * @param assetPersistence the asset persistence
	 */
	public void setAssetPersistence(AssetPersistence assetPersistence) {
		this.assetPersistence = assetPersistence;
	}

	/**
	 * Returns the checkout local service.
	 *
	 * @return the checkout local service
	 */
	public CheckoutLocalService getCheckoutLocalService() {
		return checkoutLocalService;
	}

	/**
	 * Sets the checkout local service.
	 *
	 * @param checkoutLocalService the checkout local service
	 */
	public void setCheckoutLocalService(
		CheckoutLocalService checkoutLocalService) {
		this.checkoutLocalService = checkoutLocalService;
	}

	/**
	 * Returns the checkout persistence.
	 *
	 * @return the checkout persistence
	 */
	public CheckoutPersistence getCheckoutPersistence() {
		return checkoutPersistence;
	}

	/**
	 * Sets the checkout persistence.
	 *
	 * @param checkoutPersistence the checkout persistence
	 */
	public void setCheckoutPersistence(CheckoutPersistence checkoutPersistence) {
		this.checkoutPersistence = checkoutPersistence;
	}

	/**
	 * Returns the definition local service.
	 *
	 * @return the definition local service
	 */
	public com.liferay.ams.service.DefinitionLocalService getDefinitionLocalService() {
		return definitionLocalService;
	}

	/**
	 * Sets the definition local service.
	 *
	 * @param definitionLocalService the definition local service
	 */
	public void setDefinitionLocalService(
		com.liferay.ams.service.DefinitionLocalService definitionLocalService) {
		this.definitionLocalService = definitionLocalService;
	}

	/**
	 * Returns the definition persistence.
	 *
	 * @return the definition persistence
	 */
	public DefinitionPersistence getDefinitionPersistence() {
		return definitionPersistence;
	}

	/**
	 * Sets the definition persistence.
	 *
	 * @param definitionPersistence the definition persistence
	 */
	public void setDefinitionPersistence(
		DefinitionPersistence definitionPersistence) {
		this.definitionPersistence = definitionPersistence;
	}

	/**
	 * Returns the type local service.
	 *
	 * @return the type local service
	 */
	public com.liferay.ams.service.TypeLocalService getTypeLocalService() {
		return typeLocalService;
	}

	/**
	 * Sets the type local service.
	 *
	 * @param typeLocalService the type local service
	 */
	public void setTypeLocalService(
		com.liferay.ams.service.TypeLocalService typeLocalService) {
		this.typeLocalService = typeLocalService;
	}

	/**
	 * Returns the type persistence.
	 *
	 * @return the type persistence
	 */
	public TypePersistence getTypePersistence() {
		return typePersistence;
	}

	/**
	 * Sets the type persistence.
	 *
	 * @param typePersistence the type persistence
	 */
	public void setTypePersistence(TypePersistence typePersistence) {
		this.typePersistence = typePersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.service.ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		PersistedModelLocalServiceRegistryUtil.register("com.liferay.ams.model.Checkout",
			checkoutLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"com.liferay.ams.model.Checkout");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	@Override
	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != _classLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected Class<?> getModelClass() {
		return Checkout.class;
	}

	protected String getModelClassName() {
		return Checkout.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = checkoutPersistence.getDataSource();

			DB db = DBFactoryUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.ams.service.AssetLocalService.class)
	protected com.liferay.ams.service.AssetLocalService assetLocalService;
	@BeanReference(type = AssetPersistence.class)
	protected AssetPersistence assetPersistence;
	@BeanReference(type = com.liferay.ams.service.CheckoutLocalService.class)
	protected CheckoutLocalService checkoutLocalService;
	@BeanReference(type = CheckoutPersistence.class)
	protected CheckoutPersistence checkoutPersistence;
	@BeanReference(type = com.liferay.ams.service.DefinitionLocalService.class)
	protected com.liferay.ams.service.DefinitionLocalService definitionLocalService;
	@BeanReference(type = DefinitionPersistence.class)
	protected DefinitionPersistence definitionPersistence;
	@BeanReference(type = com.liferay.ams.service.TypeLocalService.class)
	protected com.liferay.ams.service.TypeLocalService typeLocalService;
	@BeanReference(type = TypePersistence.class)
	protected TypePersistence typePersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameLocalService.class)
	protected com.liferay.portal.service.ClassNameLocalService classNameLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameService.class)
	protected com.liferay.portal.service.ClassNameService classNameService;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private String _beanIdentifier;
	private ClassLoader _classLoader;
	private CheckoutLocalServiceClpInvoker _clpInvoker = new CheckoutLocalServiceClpInvoker();
}