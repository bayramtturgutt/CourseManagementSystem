package com.dgpad.cms.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
   @NotEmpty
   private String  name;
   @Size(min = 4, max = 8)
   private String  symbol;
   @Min(2) @Max(4)
   private Integer credits;
   @NotEmpty
   private String instructorName;
   @Email
   private String instructorEmail;
}
