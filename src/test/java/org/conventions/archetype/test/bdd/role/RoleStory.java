package org.conventions.archetype.test.bdd.role;

import org.conventions.archetype.test.bdd.BaseBDD;
import org.conventions.archetype.test.bdd.Steps;
import org.conventions.archetype.test.util.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: rmpestano
 * Date: 10/31/13
 * Time: 8:18 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Arquillian.class)
@Steps(RoleSteps.class)
public class RoleStory extends BaseBDD {


    @Deployment
    public static WebArchive createDeployment()
    {
        WebArchive archive = Deployments.getBaseDeployment()
                .addPackage(BaseBDD.class.getPackage())
                .addClass(RoleSteps.class)
                .addClass(RoleStory.class)
                .addAsResource("org/conventions/archetype/test/bdd/role/role_story.story");
        MavenResolverSystem resolver = Maven.resolver();
        archive.addAsLibraries(resolver.loadPomFromFile("pom.xml").resolve("com.google.guava:guava:11.0.1").withoutTransitivity().asFile());
        archive.addAsLibraries(resolver.loadPomFromFile("pom.xml").resolve("org.jbehave:jbehave-core:3.7.5").withTransitivity().asFile());
        return archive;
    }

}
