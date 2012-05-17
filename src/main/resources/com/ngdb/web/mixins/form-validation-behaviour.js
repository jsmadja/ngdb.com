(function ($) {

    $.extend(Tapestry.Initializer, {

        configureValidation:function (specs) {
    
            /**
             * Change the way popup messages are displayed (not fixed, other css classes)
             * @param message
             */
            $.ui.tapestryFieldEventManager.prototype.showValidationMessage = function (message) {
                var field = this.element;
                var form = field.closest('form');

                this.options.validationError = true;
                form.formEventManager("setValidationError", true);
                   
                field.addClass("t-error");
                 
                this.getLabel() && this.getLabel().addClass("t-error-label");
                 
                var id = field.attr('id') + "\\:errorpopup";
                var error = $("#" + id);
                 
                if (error.size() == 0) { //if the errorpopup isn't on the page yet, we create it
                    field.before("<div id='" + field.attr('id') + ":errorpopup' class='t-error-message' />");
                }  
                
                error = $("#" + id);
                            
                error.delay(1500).hide(1000, function () {
                });
                field.hover(function () {
                        error.show();
                    }, function () {
                        if (!$("#" + $(this).attr("id") + ":focus").length) {
                            error.hide();
                        }
                    }
                );
                field.focus(function () {
                    error.show();
                });
                field.blur(function () {
                    error.hide();
                });

                field.blur(function(ev) {
                    field.tapestryFieldEventManager("removeDecorations");
                });

                Tapestry.ErrorPopup.show(error, "<div class='t-error'>" + message + "</div>");

                //show the message popup at the right place. cannot be done before adding the inner div because
                //position() has to know the height of the div.
                error.position({ my: "left center", at: "right center", of: field, collision: "flip"});

            }

            /**
             * Remove the classes added by the previous method when the field is valid
             */
            $.ui.tapestryFieldEventManager.prototype.removeDecorations = function () {
                var field = this.element;
                field.removeClass("t-error");

                this.getLabel() && this.getLabel().removeClass("t-error-label");

                $("#" + field.attr('id') + "\\:errorpopup").detach();
            }

            $.extend($.tapestry.utils, {
                /**
                 * Adds a standard validator for the element, an observer of
                 * Tapestry.FIELD_VALIDATE_EVENT. The validator function will be passed the
                 * current field value and should throw an error message if the field's
                 * value is not valid.
                 *
                 * @param element
                 *            field element to validate
                 * @param validator
                 *            function to be passed the field value
                 */
                addValidator : function(element, validator) {
                    $(element).bind(Tapestry.FIELD_VALIDATE_EVENT, function(e, data, translated) {
                            try {
                                validator.call(this, translated);
                            } catch (message) {
                    
                                $(element).tapestryFieldEventManager("showValidationMessage", message);
                            }
                        }
                    )
                        ;

                    return element;
                }
            });
                   
        }
                    
    });
})(jQuery);
