package com.app.kokonut.woody.common.security.bean;

import com.app.kokonut.woody.been.KokonutUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;


public class AuthUser implements UserDetails{

	private static final long serialVersionUID = 6933809189401599135L;
	
	private KokonutUser user;
	private HashMap<String, String> usersSettings;
	private Collection<? extends GrantedAuthority> authorities;
	private UserRole userRole;
	private int serverRole = 5;
	private int concurrency = 0;
	  
	public UserRole getUserRole(){
		return this.userRole;
	}
	  
	public void setUserRole(UserRole userRole){
		this.userRole = userRole;
	}

	public AuthUser(KokonutUser user){
		this.user = user;
	}
	
	public void setUser(KokonutUser user) {
		this.user = user;
	}
	  
	public KokonutUser getUser(){
	    return this.user;
	}
	  
	public HashMap<String, String> getUsersSettings(){
	    return this.usersSettings;
	}
	  
	public HashMap<String, String> getUsersSettingsByPrefix(String prefix){
	    HashMap<String, String> rtnValue = new HashMap<String, String>();
	    for (String key : this.usersSettings.keySet()) {
	    	if (key.startsWith(prefix)) {
	    		rtnValue.put(key, this.usersSettings.get(key));
	    	}
	    }
	    return rtnValue;
	}
	  
	public void removeUsersSettingsByPrefix(String prefix){
		for (String key : this.usersSettings.keySet()) {
	    	if (key.startsWith(prefix)){
	    		this.usersSettings.remove(key);
	    	}
	    }
	}
	  
	public void removeUsersSettings(String key){
		if (this.usersSettings.containsKey(key))
	    {
	    	this.usersSettings.remove(key);
	    }
	}
	  
	public void setGrantedAuthorities(Collection<? extends GrantedAuthority> authorities){
		this.authorities = authorities;
	}
	  
	public boolean equals(Object o){
		
		boolean isFind = false;
		AuthUser other = (AuthUser)o;
		do{
			if (!(o instanceof AuthUser)) {
				break;
			}
			
			if(this.getUser() == null){
				break;
			}
			if(other.getUser() == null){
				break;
			}
			
			
			other.setConcurrency(1); 
			isFind = true;
			
		}
		while(false);
		
		return isFind;
		
	}
	  
	public int hashCode(){
		return 0;//this.user!=null?this.user.getIdx().hashCode():null;
	}
	  
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return this.authorities;
	}
	  
	public String getPassword(){
		return null;
	}
	  
	public boolean isAccountNonExpired(){
		return true;
	}
	  
	public boolean isEnabled(){
		return true;
	}
	  
	public int getServerRole(){
		return this.serverRole;
	}
	  
	public void setServerRole(int serverRole){
		this.serverRole = serverRole;
	}
	
	public int getConcurrency() {
		return concurrency;
	}

	public void setConcurrency(int concurrency) {
		this.concurrency = concurrency;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
		
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String getUsername() {
		return user.getName();
	}
	
}
