package org.jenkins.plugins.lockableresources;

import static org.junit.Assert.assertEquals;

import hudson.util.FormValidation;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class LockableResourceManagerTest {

  @Rule public JenkinsRule j = new JenkinsRule();

  @Test
  public void validationFailure() {
    RequiredResourcesProperty.DescriptorImpl d = new RequiredResourcesProperty.DescriptorImpl();
    LockableResourcesManager.get().createResource("resource1");
    LockableResource r = LockableResourcesManager.get().getResources().get(0);
    r.setLabels("some-label");

    assertEquals(
      "Only label, groovy expression, or resources can be defined, not more than one.",
      d.doCheckResourceNames("resource1", null, true, null).getMessage());
    assertEquals(
      "Only label, groovy expression, or resources can be defined, not more than one.",
      d.doCheckResourceNames("resource1", "some-label", false, null).getMessage());
    assertEquals(
      "Only label, groovy expression, or resources can be defined, not more than one.",
      d.doCheckResourceNames("resource1", "some-label", true, null).getMessage());
    assertEquals(
      "Only label, groovy expression, or resources can be defined, not more than one.",
      d.doCheckLabelName("some-label", "resource1", false, null).getMessage());
    assertEquals(
      "Only label, groovy expression, or resources can be defined, not more than one.",
      d.doCheckLabelName("some-label", null, true, null).getMessage());
    assertEquals(
      "Only label, groovy expression, or resources can be defined, not more than one.",
      d.doCheckLabelName("some-label", "resource1", true, null).getMessage());

    assertEquals(FormValidation.ok(), d.doCheckResourceNames("resource1", null, false, null));
    assertEquals(FormValidation.ok(), d.doCheckLabelName("some-label", null, false, null));
  }
}
