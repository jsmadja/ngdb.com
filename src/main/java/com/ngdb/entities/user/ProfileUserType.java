package com.ngdb.entities.user;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.type.StringType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.hibernate.usertype.UserType;

public class ProfileUserType implements UserType {

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		String value = (String) StringType.INSTANCE.get(rs, names[0]);
		if (value == null)
			return null;
		else
			return Profile.valueOf(value);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if (value == null)
			StringType.INSTANCE.set(st, null, index);
		else
			StringType.INSTANCE.set(st, value.toString(), index);
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { VarcharTypeDescriptor.INSTANCE.getSqlType() };
	}

	@Override
	public Class<Profile> returnedClass() {
		return Profile.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x.equals(y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

}
