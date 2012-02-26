package com.google.sitebricks.compiler.template;

import com.google.common.collect.ImmutableSet;
import com.google.sitebricks.Renderable;
import com.google.sitebricks.Respond;
import com.google.sitebricks.Template;
import com.google.sitebricks.compiler.TemplateRenderer;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import java.util.HashMap;
import java.util.Set;

/**
 * Creates renderables, given an MVEL template page.
 */
public class MvelTemplateCompiler implements TemplateRenderer {

  public Renderable compile(final Class<?> page, final Template template) {
    // Compile template immediately.
    final CompiledTemplate compiledTemplate = TemplateCompiler.compileTemplate(template.getText());

    return new Renderable() {
      @Override
      public void render(Object bound, Respond respond) {
        assert page.isInstance(bound);
        respond.write(TemplateRuntime.execute(compiledTemplate, bound, new HashMap()).toString());
      }

      @Override
      public <T extends Renderable> Set<T> collect(Class<T> clazz) {
        return ImmutableSet.of();
      }
    };
  }
}
