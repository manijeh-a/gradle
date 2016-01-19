/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.plugins.ide.internal.tooling.java;

import org.gradle.api.JavaVersion;

import java.io.Serializable;

public class DefaultJavaSourceSettings implements Serializable {
    private JavaVersion sourceLanguageLevel;
    private JavaVersion targetBytecodeLevel;
    private DefaultJavaInstallation targetRuntime;

    public DefaultJavaSourceSettings setSourceLanguageLevel(JavaVersion sourceLanguageLevel) {
        this.sourceLanguageLevel = sourceLanguageLevel;
        return this;
    }

    public DefaultJavaSourceSettings setTargetBytecodeLevel(JavaVersion targetBytecodeLevel) {
        this.targetBytecodeLevel = targetBytecodeLevel;
        return this;
    }

    public DefaultJavaSourceSettings setTargetRuntime(DefaultJavaInstallation targetRuntime) {
        this.targetRuntime = targetRuntime;
        return this;
    }

    public JavaVersion getSourceLanguageLevel() {
        return sourceLanguageLevel;
    }

    public JavaVersion getTargetBytecodeLevel() {
        return targetBytecodeLevel;
    }

    public DefaultJavaInstallation getTargetRuntime() {
        return targetRuntime;
    }
}
