package com.ngdb.entities.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embeddable;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.hibernate.annotations.TypeDef;

@Embeddable
@TypeDef(defaultForType = Profile.class, typeClass = ProfileUserType.class)
public enum Profile {

	GUEST("dashboard:*"),

	WRITER("gestion:*", "dashboard:*", "administration:logmanagement"),

	ADMIN("administration:*", "dashboard:*");

	private Set<Permission> permissions = new HashSet<Permission>();

	private Profile(String... wildcardPermissions) {
		for (String permission : wildcardPermissions) {
			permissions.add(new WildcardPermission(permission));
		}
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public boolean implies(String permissionDefinition) {
		Permission permissionDemandee = new WildcardPermission(permissionDefinition);
		for (Permission permission : permissions) {
			if (permission.implies(permissionDemandee)) {
				return true;
			}
		}
		return false;
	}
}