Storage.prototype.setObject = function(key, value) {
    this.setItem(key, JSON.stringify(value));
}

Storage.prototype.getObject = function(key) {
    var value = this.getItem(key);

    return value && JSON.parse(value);
}

Storage.prototype.isLoggedUserRole = function(roles) {
    var loggedUser = this.getObject("loggedUser");
	
	if(!loggedUser && roles.find(role => role === "") != undefined){
		return true;
	}
	
	if(!loggedUser){
		return false;
	}
	
	return roles.find(role => role === loggedUser.user.role) != undefined;
}