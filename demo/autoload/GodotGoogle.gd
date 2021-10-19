extends Node

signal signed_in
signal signin_error( p_error_code )
signal signed_out

var _GoogleModule : Object = null


func _ready():
	if Engine.has_singleton("GodotGoogle"):
		_GoogleModule = Engine.get_singleton("GodotGoogle")
		
		_GoogleModule.connect("signed_in", self, "_on_signed_in")
		_GoogleModule.connect("signin_error", self, "_on_signin_error")
		_GoogleModule.connect("signed_out", self, "_on_signed_out")
	else:
		printerr("GodotGoogle singleton not found")


# Module Methods
func sign_in() -> void:
	if not _is_valid():
		return
	
	_GoogleModule.signIn()

func sign_out() -> void:
	if not _is_valid():
		return
	
	_GoogleModule.signOut()

func is_signed() -> bool:
	if not _is_valid():
		return false
	
	return _GoogleModule.isSigned()
	
func get_id() -> String:
	if not _is_valid():
		return ""
	
	return _GoogleModule.getId()

func get_first_name() -> String:
	if not _is_valid():
		return ""
	
	return _GoogleModule.getFirstName()

func get_display_name() -> String:
	if not _is_valid():
		return ""
	
	return _GoogleModule.getDisplayName()

func get_picture_url() -> String:
	if not _is_valid():
		return ""
	
	return _GoogleModule.getPictureUri()

func _is_valid() -> bool:
	if _GoogleModule:
		return true
	else:
		printerr("GodotGoogle singleton is null")
		return false

# Signals
func _on_signed_in() -> void:
	emit_signal("signed_in")

func _on_signin_error( p_error_code : int ) -> void:
	emit_signal("signin_error", p_error_code)

func _on_signed_out() -> void:
	emit_signal("signed_out")
