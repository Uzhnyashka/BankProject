package com.bankproject.objects;


/**
 * Created by bobyk on 14/05/16.
 */


public class User {

  /*  @NotNull(message="Имя должно быть задано")
    String firstname;

    @NotNull(message="Фамилия должна быть задана")
    @Size(min = 3, message="Длина фамилии должна быть больше трех")
    String lastname;

    @NotNull(message="Имэйл должен быть задан")
    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
            "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
            "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
            "+(?:[a-zA-Z]){2,}\\.?)$",
            message = "заданный имэйл не может существовать")
    String email;

    @Override
    public String toString() {
        return String.format("firstname: [%s], lastname: [%s], email: [%s]",
                firstname, lastname, email);
    }

    public static void validate(Object object, Validator validator) {
        Set<ConstraintViolation<Object>> constraintViolations = validator
                .validate(object);

        System.out.println(object);
        System.out.println(String.format("Кол-во ошибок: %d",
                constraintViolations.size()));

        for (ConstraintViolation<Object> cv : constraintViolations)
            System.out.println(String.format(
                    "Внимание, ошибка! property: [%s], value: [%s], message: [%s]",
                    cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
    }

    public static void main(String[] args) {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();

        User user = new User();
        validate(user, validator);

        user.firstname = "Вася";
        validate(user, validator);

        user.lastname = "Пу";
        validate(user, validator);

        user.lastname = "Пупкин";
        validate(user, validator);

        user.email = "вася пупкин@example.com";
        validate(user, validator);

        user.email = "vasya.poupkine@example.com";
        validate(user, validator);

    }*/

}

