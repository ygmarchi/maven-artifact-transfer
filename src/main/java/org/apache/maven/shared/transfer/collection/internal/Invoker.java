package org.apache.maven.shared.transfer.collection.internal;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.maven.shared.transfer.dependencies.collect.DependencyCollectorException;
import org.apache.maven.shared.transfer.collection.DependencyCollectionException;

/**
 * Invokes method on objects using reflection.
 */
final class Invoker
{
    private Invoker()
    {
        // do not instantiate
    }

    public static <T> T invoke( Object object, String method )
        throws DependencyCollectionException
    {
        @SuppressWarnings( "unchecked" )
        T invoke = (T) invoke( object.getClass(), object, method );
        return invoke;
    }

    @SuppressWarnings( "unchecked" )
    public static <T> T invoke( Class<?> objectClazz, Object object, String method )
        throws DependencyCollectionException
    {
        try
        {
            return (T) objectClazz.getMethod( method ).invoke( object );
        }
        catch ( IllegalAccessException e )
        {
            throw new DependencyCollectionException( e.getMessage(), e );
        }
        catch ( InvocationTargetException e )
        {
            throw new DependencyCollectionException( e.getMessage(), e );
        }
        catch ( NoSuchMethodException e )
        {
            throw new DependencyCollectionException( e.getMessage(), e );
        }
    }

    public static <T> T invoke( Class<?> objectClazz, String staticMethod, Class<?> argClazz, Object arg )
                    throws DependencyCollectionException
    {
        try
        {
            Method method = objectClazz.getMethod( staticMethod, argClazz );
            @SuppressWarnings( "unchecked" )
            T invoke = (T) method.invoke( null, arg );
            return invoke;
        }
        catch ( IllegalAccessException e )
        {
            throw new DependencyCollectionException( e.getMessage(), e );
        }
        catch ( InvocationTargetException e )
        {
            throw new DependencyCollectionException( e.getMessage(), e );
        }
        catch ( NoSuchMethodException e )
        {
            throw new DependencyCollectionException( e.getMessage(), e );
        }
    }
    
    /**
     * <strong>Note:</strong> Ensure that argClasses and args have the same number of elements 
     * 
     * @param objectClazz the class of the static method
     * @param staticMethod the static method to call
     * @param argClasses the classes of the argument, used to select the right static method
     * @param args the actual arguments to be passed
     * @return the result of the method invocation
     * @throws DependencyCollectorException if any checked exception occurs
     */
    public static <T> T invoke( Class<?> objectClazz, String staticMethod, Class<?>[] argClasses, Object[] args )
                    throws DependencyCollectionException
    {
        try
        {
            @SuppressWarnings( "unchecked" )
            T invoke = (T) objectClazz.getMethod( staticMethod, argClasses ).invoke( null, args );
            return invoke;
        }
        catch ( IllegalAccessException e )
        {
            throw new DependencyCollectionException( e.getMessage(), e );
        }
        catch ( InvocationTargetException e )
        {
            throw new DependencyCollectionException( e.getMessage(), e );
        }
        catch ( NoSuchMethodException e )
        {
            throw new DependencyCollectionException( e.getMessage(), e );
        }
    }

}
