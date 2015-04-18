/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.particles.spawner;

import com.stevenlr.waffle.particles.Particle;

public interface IParticleSpawnerDecorator {

	boolean canSpawnParticle();
	boolean isDoneSpawning();
	Particle spawnParticle();
}
