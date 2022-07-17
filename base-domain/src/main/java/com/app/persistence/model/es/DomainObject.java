package com.app.persistence.model.es;

public abstract class DomainObject {

	public abstract String getId();

	public abstract void setId(String id);

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.getId() == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
			return false;
		}
		DomainObject that = (DomainObject) obj;
		return this.getId().equals(that.getId());
	}
}
