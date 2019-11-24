package com.ant.sso.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class EmailTemplate {

    @Id
    @GeneratedValue
    private Long emailTemplateId;

    @Column(name = "template_name")
    private String templateName;

    @Column(name="template_content")
    private String templateContent;

    private Short state=0;

    private String remark;
}
