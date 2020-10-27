/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection.java;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.hibernate.annotations.common.reflection.AnnotationReader;
import org.hibernate.annotations.common.reflection.ClassLoaderDelegate;
import org.hibernate.annotations.common.reflection.ClassLoadingException;
import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XMethod;
import org.hibernate.annotations.common.reflection.XPackage;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.MetadataProviderInjector;
import org.hibernate.annotations.common.reflection.MetadataProvider;
import org.hibernate.annotations.common.reflection.java.generics.IdentityTypeEnvironment;
import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironmentFactory;
import org.hibernate.annotations.common.reflection.java.generics.TypeSwitch;
import org.hibernate.annotations.common.reflection.java.generics.TypeUtils;
import org.hibernate.annotations.common.Version;
import org.hibernate.annotations.common.util.ReflectHelper;
import org.hibernate.annotations.common.util.StandardClassLoaderDelegateImpl;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

/**
 * The factory for all the objects in this package.
 *
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 * @author Emmanuel Bernard
 * @author Sanne Grinovero
 */
public final class JavaReflectionManager implements ReflectionManager, MetadataProviderInjector {

	private static final boolean METADATA_CACHE_DIAGNOSTICS = Boolean.getBoolean( "org.hibernate.annotations.common.METADATA_CACHE_DIAGNOSTICS" );

	private MetadataProvider metadataProvider;
	private ClassLoaderDelegate classLoaderDelegate = StandardClassLoaderDelegateImpl.INSTANCE;

	private final AtomicBoolean empty = new AtomicBoolean(true);

	static {
		LoggerFactory.make( Version.class.getName() ).version( Version.getVersionString() );
	}

	public MetadataProvider getMetadataProvider() {
		if (metadataProvider == null) {
			setMetadataProvider( new JavaMetadataProvider() );
		}
		return metadataProvider;
	}

	public void setMetadataProvider(MetadataProvider metadataProvider) {
		this.metadataProvider = metadataProvider;
	}

	@Override
	public void injectClassLoaderDelegate(ClassLoaderDelegate delegate) {
		if ( delegate == null ) {
			this.classLoaderDelegate = StandardClassLoaderDelegateImpl.INSTANCE;
		}
		else {
			this.classLoaderDelegate = delegate;
		}
	}

	@Override
	public ClassLoaderDelegate getClassLoaderDelegate() {
		return classLoaderDelegate;
	}

	private final TypeEnvironmentMap<Class,JavaXClass> xClasses = new TypeEnvironmentMap<>( this::javaXClassConstruction );

	private JavaXClass javaXClassConstruction(
			Class classType,
			TypeEnvironment typeEnvironment) {
		used();
		return new JavaXClass( classType, typeEnvironment, this );
	}

	private Map<Package, JavaXPackage> packagesToXPackages;

	private final TypeEnvironmentMap<Member, JavaXProperty> xProperties = new TypeEnvironmentMap<>( this::javaXPropertyConstruction );

	private JavaXProperty javaXPropertyConstruction(Member member, TypeEnvironment typeEnvironment) {
		used();
		return JavaXProperty.create( member, typeEnvironment, this );
	}

	private final TypeEnvironmentMap<Member, JavaXMethod> xMethods = new TypeEnvironmentMap<>( this::javaJavaXMethodConstruction );

	private JavaXMethod javaJavaXMethodConstruction(Member member, TypeEnvironment typeEnvironment) {
		used();
		return JavaXMethod.create( member, typeEnvironment, this );
	}

	public XClass toXClass(Class clazz) {
		return toXClass( clazz, IdentityTypeEnvironment.INSTANCE );
	}

	public Class toClass(XClass xClazz) {
		if ( ! ( xClazz instanceof JavaXClass ) ) {
			throw new IllegalArgumentException( "XClass not coming from this ReflectionManager implementation" );
		}
		return (Class) ( (JavaXClass) xClazz ).toAnnotatedElement();
	}

	public Method toMethod(XMethod xMethod) {
		if ( ! ( xMethod instanceof JavaXMethod ) ) {
			throw new IllegalArgumentException( "XMethod not coming from this ReflectionManager implementation" );
		}
		return (Method) ( (JavaXAnnotatedElement) xMethod ).toAnnotatedElement();
	}

	@Override
	@Deprecated
	@SuppressWarnings("deprecation")
	public XClass classForName(String name, Class caller) throws ClassNotFoundException {
		return toXClass( ReflectHelper.classForName( name, caller ) );
	}

	@Override
	public XClass classForName(String name) throws ClassLoadingException {
		return toXClass( getClassLoaderDelegate().classForName( name ) );
	}

	@Override
	public XPackage packageForName(String packageName) {
		return getXAnnotatedElement( getClassLoaderDelegate().classForName( packageName + ".package-info" ).getPackage() );
	}

	XClass toXClass(Type t, final TypeEnvironment context) {
		return new TypeSwitch<XClass>() {
			@Override
			public XClass caseClass(Class classType) {
				return xClasses.getOrCompute( context, classType );
			}

			@Override
			public XClass caseParameterizedType(ParameterizedType parameterizedType) {
				return toXClass( parameterizedType.getRawType(),
					TypeEnvironmentFactory.getEnvironment( parameterizedType, context )
				);
			}
		}.doSwitch( context.bind( t ) );
	}

	@Deprecated
	XPackage getXAnnotatedElement(Package pkg) {
		return toXPackage( pkg );
	}

	@Override
	public XPackage toXPackage(Package pkg) {
		final Map<Package, JavaXPackage> packagesToXPackagesMap = getPackagesToXPackagesMap();
		JavaXPackage xPackage = packagesToXPackagesMap.get( pkg );
		if ( xPackage == null ) {
			xPackage = new JavaXPackage( pkg, this );
			used();
			packagesToXPackagesMap.put( pkg, xPackage );
		}
		return xPackage;
	}

	private Map<Package, JavaXPackage> getPackagesToXPackagesMap() {
		if ( this.packagesToXPackages == null ) {
			this.packagesToXPackages = new HashMap<>( 8, 0.5f );
		}
		return this.packagesToXPackages;
	}

	XProperty getXProperty(Member member, TypeEnvironment context) {
		return xProperties.getOrCompute( context, member );
	}

	XMethod getXMethod(Member member, TypeEnvironment context) {
		return xMethods.getOrCompute( context, member );
	}

	TypeEnvironment getTypeEnvironment(final Type t) {
		return new TypeSwitch<TypeEnvironment>() {
			@Override
			public TypeEnvironment caseClass(Class classType) {
				return TypeEnvironmentFactory.getEnvironment( classType );
			}

			@Override
			public TypeEnvironment caseParameterizedType(ParameterizedType parameterizedType) {
				return TypeEnvironmentFactory.getEnvironment( parameterizedType );
			}

			@Override
			public TypeEnvironment defaultCase(Type type) {
				return IdentityTypeEnvironment.INSTANCE;
			}
		}.doSwitch( t );
	}

	public JavaXType toXType(TypeEnvironment context, Type propType) {
		Type boundType = toApproximatingEnvironment( context ).bind( propType );
		if ( TypeUtils.isArray( boundType ) ) {
			return new JavaXArrayType( propType, context, this );
		}
		if ( TypeUtils.isCollection( boundType ) ) {
			return new JavaXCollectionType( propType, context, this );
		}
		if ( TypeUtils.isSimple( boundType ) ) {
			return new JavaXSimpleType( propType, context, this );
		}
		throw new IllegalArgumentException( "No PropertyTypeExtractor available for type void " );
	}

	public boolean equals(XClass class1, Class class2) {
		if ( class1 == null ) {
			return class2 == null;
		}
		return ( (JavaXClass) class1 ).toClass().equals( class2 );
	}

	public TypeEnvironment toApproximatingEnvironment(TypeEnvironment context) {
		return TypeEnvironmentFactory.toApproximatingEnvironment( context );
	}

    public AnnotationReader buildAnnotationReader(AnnotatedElement annotatedElement) {
        return getMetadataProvider().getAnnotationReader( annotatedElement );
    }
    
    public Map getDefaults() {
        return getMetadataProvider().getDefaults();
    }

	@Override
	public void reset() {
		boolean wasEmpty = empty.getAndSet( true );
		if ( !wasEmpty ) {
			this.xClasses.clear();
			this.packagesToXPackages = null;
			this.xProperties.clear();
			this.xMethods.clear();
			if ( METADATA_CACHE_DIAGNOSTICS ) {
				new RuntimeException( "Diagnostics message : Caches now empty" ).printStackTrace();
			}
		}
		if ( metadataProvider != null ) {
			this.metadataProvider.reset();
		}
	}

	private void used() {
		boolean wasEmpty = empty.getAndSet( false );
		if ( wasEmpty && METADATA_CACHE_DIAGNOSTICS ) {
			new RuntimeException( "Diagnostics message : Caches now being used" ).printStackTrace();
		}
	}

}
