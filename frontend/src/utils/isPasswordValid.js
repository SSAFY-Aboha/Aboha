const isPasswordValid = password => {
  const specialCharRegex = /[!@#$%^&*(),.?":{}|<>]/
  return password.length >= 8 && specialCharRegex.test(password)
}

export { isPasswordValid }
