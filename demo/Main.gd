extends Control

func _ready():
	GodotGoogle.connect("signed_in", self, "_on_signed_in")
	GodotGoogle.connect("signin_error", self, "_on_signin_error")
	GodotGoogle.connect("signed_out", self, "_on_signed_out")
	
	show_signout_button() if GodotGoogle.is_signed() else show_signin_button()

#Buttons
func _on_SignInButton_pressed():
	GodotGoogle.sign_in()

func _on_SignOutButton_pressed():
	GodotGoogle.sign_out()

func show_signin_button() -> void:
	get_node("SignInButton").show()
	get_node("SignOutButton").hide()

func show_signout_button() -> void:
	get_node("SignInButton").hide()
	get_node("SignOutButton").show()


#Signals
func _on_signed_in() -> void:
	print("Signed In")
	show_signout_button()

func _on_signin_error( p_error_code : int ) -> void:
	print("SignIn Error, Code = ", p_error_code)
	show_signin_button()

func _on_signed_out() -> void:
	print("Signed Out")
	show_signin_button()

