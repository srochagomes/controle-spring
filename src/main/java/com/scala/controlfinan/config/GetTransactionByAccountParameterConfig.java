package com.scala.controlfinan.config;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
        @ApiImplicitParam(name = "startedAt", dataType = "string", paramType = "query",value = "start period", required = false, example = "01-03-2021"),
        @ApiImplicitParam(name = "endedAt", dataType = "string", paramType = "query",value = "end period", required = false, example = "01-03-2021"),
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                value = "Sorting criteria in the format: property(,asc|desc). " +
                        "Default sort order is ascending. " +
                        "Multiple sort criteria are supported.")
})
@ApiResponses( value  = {
        @ApiResponse( code  =  200 , message  =  "Operation success" ),
        @ApiResponse( code  =  401 , message  =  "Unauthorized" ),
        @ApiResponse ( code  =  403 , message  =  "Forbidden" ),
        @ApiResponse ( code  =  404 , message  =   "Not Found" ),
        @ApiResponse ( code = 500, message = "API Unavailable")
})
public @interface GetTransactionByAccountParameterConfig {
}
