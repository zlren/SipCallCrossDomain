/**
 * DIALOGIC CONFIDENTIAL
 * Copyright (C) 2005-2013 Dialogic Corporation. All Rights Reserved.
 *
 * The source code contained or described herein and all documents related to
 * the source code ("Material") are owned by Dialogic Corporation or its
 * suppliers or licensors.  Title to the Material remains with Dialogic
 * Corporation or its suppliers and licensors.  The Material contains trade
 * secrets and proprietary and confidential information of Dialogic or its
 * suppliers and licensors.  The Material is protected by worldwide copyright
 * and trade secret laws and treaty provisions.  No part of the Material may be
 * used, copied, reproduced, modified, published, uploaded, posted, transmitted,
 * distributed, or disclosed in any way without Dialogic's prior express written
 * permission.
 *
 * No license under any patent, copyright, trade secret or other intellectual
 * property right is granted to or conferred upon you by disclosure or delivery
 * of the Materials, either expressly, by implication, inducement, estoppel or
 * otherwise.  Any license under such intellectual property rights must be
 * express and approved by Dialogic in writing.
 */

package testing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgcDemoProperty {

	protected Properties demoProps;
	final String DEFAULT_DIALOGIC_PROP_NAME = "DIALOGIC_DEMO_PROPERTY_FILE";
	static Logger log = LoggerFactory.getLogger(DlgcDemoProperty.class);

	public DlgcDemoProperty(Class<?> cls) {
		this.LoadProperties(cls);
	}

	public Properties getProperties() {
		return demoProps;
	}

	public String getProperty(String key) {
		String val = null;
		val = demoProps.getProperty(key);
		return val;
	}

	public Properties reloadProperties() {
		this.LoadProperties(this.getClass());
		return demoProps;
	}

	protected Properties LoadProperties(Class<?> cls) {
		demoProps = new Properties();
		String configPath = System.getenv(DEFAULT_DIALOGIC_PROP_NAME);

		if (configPath == null) {
			log.warn((new StringBuilder()).append("Configuration File: ").append(DEFAULT_DIALOGIC_PROP_NAME)
					.append(" not provided").toString());

			try {
				demoProps.load(cls.getClassLoader().getResourceAsStream(DEFAULT_DIALOGIC_PROP_NAME));
				log.info("Configuration File: " + DEFAULT_DIALOGIC_PROP_NAME + " Successfully Loaded");
			} catch (Exception e) {
				log.error(e.toString());
				demoProps = null;
			}
		} else {
			try {
				FileInputStream inputStream = new FileInputStream(configPath);
				demoProps.load(inputStream);
				inputStream.close();
				log.info("Configuration File: " + configPath + " Successfully Loaded");
			} catch (IOException ioe) {
				log.error(ioe.toString());
				log.error((new StringBuilder()).append("Configuration File: ").append(configPath).append(" load failed")
						.toString());
				log.info("Switching to default configuration file" + DEFAULT_DIALOGIC_PROP_NAME);

				try {
					demoProps.load(cls.getClassLoader().getResourceAsStream(DEFAULT_DIALOGIC_PROP_NAME));
					log.info("Configuration File: " + DEFAULT_DIALOGIC_PROP_NAME + " Successfully Loaded");
				} catch (Exception e) {
					log.error(e.toString());
					demoProps = null;
				}
			}
		}

		return demoProps;
	}

}
