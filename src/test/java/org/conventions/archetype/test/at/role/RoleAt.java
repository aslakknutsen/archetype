package org.conventions.archetype.test.at.role;

import org.conventions.archetype.test.at.BaseAt;
import org.conventions.archetype.test.at.logon.LogonStep;
import org.conventions.archetype.test.bdd.Steps;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: rmpestano
 * Date: 10/31/13
 * Time: 8:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Steps({ RoleStep.class, LogonStep.class })
@RunWith(Arquillian.class)
public class RoleAt extends BaseAt {


	@Deployment(testable = false)
    public static WebArchive createDeployment()
    {
        WebArchive archive = createBaseDeployment();

        System.out.println(archive.toString(true));
        return archive;
    }


	
}
