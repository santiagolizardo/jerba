package com.santiagolizardo.jerba.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Article {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Date publicationDate;

	@Persistent
	private Date modificationDate;

	@Persistent
	private int position;

	@Persistent
	private int numViews;

	@Persistent
	private ArticleType type;

	@Persistent
	private String title;
	
	@Persistent
	private String sanitizedTitle;

	@Persistent
	private String description;

	@Persistent
	private String keywords;

	@Persistent
	private Text content;

	@Persistent
	private Key parent;

	@NotPersistent
	private List<Article> children;

	@Persistent
	private boolean visible;

	public Article() {
		children = new ArrayList<Article>();
		visible = true;
		position = 0;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getNumViews() {
		return numViews;
	}

	public void setNumViews(int numViews) {
		this.numViews = numViews;
	}

	public ArticleType getType() {
		return type;
	}

	public void setType(ArticleType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSanitizedTitle() {
		return sanitizedTitle;
	}
	
	public void setSanitizedTitle(String sanitizedTitle) {
		this.sanitizedTitle = sanitizedTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Text getContent() {
		return content;
	}

	public void setContent(Text content) {
		this.content = content;
	}

	public Key getParent() {
		return parent;
	}

	public void setParent(Key parent) {
		this.parent = parent;
	}

	public List<Article> getChildren() {
		return children;
	}

	public void setChildren(List<Article> children) {
		this.children = children;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result
				+ ((keywords == null) ? 0 : keywords.hashCode());
		result = prime
				* result
				+ ((modificationDate == null) ? 0 : modificationDate.hashCode());
		result = prime * result + numViews;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + position;
		result = prime * result
				+ ((publicationDate == null) ? 0 : publicationDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (visible ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (keywords == null) {
			if (other.keywords != null)
				return false;
		} else if (!keywords.equals(other.keywords))
			return false;
		if (modificationDate == null) {
			if (other.modificationDate != null)
				return false;
		} else if (!modificationDate.equals(other.modificationDate))
			return false;
		if (numViews != other.numViews)
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (position != other.position)
			return false;
		if (publicationDate == null) {
			if (other.publicationDate != null)
				return false;
		} else if (!publicationDate.equals(other.publicationDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (visible != other.visible)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Article [children=" + children + ", content=" + content
				+ ", description=" + description + ", key=" + key
				+ ", keywords=" + keywords + ", modificationDate="
				+ modificationDate + ", numViews=" + numViews
				+ ", publicationDate=" + publicationDate + ", title=" + title
				+ ", type=" + type + "]";
	}
}
